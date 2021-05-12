package org.bookmc.mod;

public class ModTest {
    private boolean isDevelopmentEnvironment = false;

    {
        try {
            //noinspection ConstantConditions
            isDevelopmentEnvironment = Class.forName("net.minecraft.client.Minecraft") != null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (isDevelopmentEnvironment) {
            System.out.println("Thanks for noticing me!");
        }
    }

    public void anotherStart() {
        if (isDevelopmentEnvironment) {
            System.out.println("Maybe I want this to be separate for cool reasons?");
        }
    }
}
