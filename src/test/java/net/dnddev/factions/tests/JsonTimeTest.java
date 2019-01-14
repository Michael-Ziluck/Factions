package net.dnddev.factions.tests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Chars;
import net.dnddev.factions.base.User;
import net.dnddev.factions.data.mongodb.MongoUser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class JsonTimeTest
{

    private HashMap<UUID, MongoUser> users = new HashMap<>();

    private ObjectMapper mapper;

    @Before
    public void setup()
    {
        mapper = new ObjectMapper();
        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                                          .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                          .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                          .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                          .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    @Test
    public void testBulkSave()
    {
        Random r = new Random();
        Charset set = Charset.forName("UTF-8");
        MongoUser user;
        System.out.println("Starting setup...");
        for (int i = 0; i < 10_000; i++)
        {
            user = generateUser(r, set);
            users.put(user.getUniqueId(), user);
        }
        System.out.println("Finished setup.");

        try
        {
            long start = System.currentTimeMillis();
            System.out.println("Starting write...");
            mapper.writeValue(new File("users.json"), users);
            System.out.println("Finished write. Time: " + (System.currentTimeMillis() - start));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSingleSave() throws IOException
    {
        User user;
        System.out.println("Starting write...");
        long total = 0;
        long start;
        for (int i = 0; i < 10_000; i++)
        {
            user = generateUser(new Random(), Charset.forName("UTF-8"));
            start = System.nanoTime();
            mapper.writeValue(new File("users", user.getUniqueId().toString()), user);
            total += (System.nanoTime() - start);
        }
        System.out.println("Finished write. Average Time: " + (total / 1_000_000_000.0));
    }

    @Test
    public void testBulkLoad() throws IOException
    {
        File[] files = new File("users").listFiles();

        long start = System.nanoTime();
        for (File file : files)
        {
            users.put(UUID.fromString(file.getName()), mapper.readValue(file, MongoUser.class));
        }
        System.out.println("Finished bulk read: " + (System.nanoTime() - start));
    }

    @Test
    public void testSingleLoad() throws IOException
    {
        File file = new File("users.json");
    }

    private MongoUser generateUser(Random r, Charset charset)
    {
        byte[] arr = new byte[7];
        r.nextBytes(arr);

        return new MongoUser(r.nextLong(), new UUID(r.nextLong(), r.nextLong()), new String(arr, charset));
    }

}
