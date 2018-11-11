package me.pfrison.polytimeima4.clicker.upgrade;

public class Upgrade {
    public static final int INGREDIENT = 0;
    public static final int SUPPLEMENT = 1;
    public static final int GLOVE = 2;
    public static final int GOLDEN = 3;

    public int type;
    public int[] identifier;
    /**
     * Identifier
     *
     * INGREDIENTS : [ingredient][level]
     * SUPPLEMENTS : [level]
     * GLOVE :       [level]
     * GOLDEN :      [level]
     *
     */

    public Upgrade(int type, int... identifier){
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Upgrade))
            return false;
        Upgrade upgrade = (Upgrade) obj;

        if(type != upgrade.type)
            return false;

        if(identifier == null && upgrade.identifier == null)
            return true;
        if(identifier == null || upgrade.identifier == null)
            return false;
        if(identifier.length != upgrade.identifier.length)
            return false;

        for (int i=0; i<identifier.length; i++)
            if (identifier[i] != upgrade.identifier[i])
                return false;
        return true;
    }
}
