/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommytony.war.config;

/**
 *
 * @author Austin
 */
public enum MonumentMode 
{
    MEDIC("Medic"),
    COMMAND("Command"),
    CAPTURE("Capture");
    
    private String mode;
    private MonumentMode(String m)
    {
        mode=m;
    }
    
    public String getName()
    {
        return mode;
    }

    public boolean equals(MonumentMode m)
    {
        if(m.getName().equalsIgnoreCase(mode))
            return true;
        return false;
    }
    
    public static MonumentMode getEnum(String s)
    {
        if(s.equalsIgnoreCase("Medic"))
            return MEDIC;
        else if(s.equalsIgnoreCase("Command"))
            return COMMAND;
        else if(s.equalsIgnoreCase("Capture"))
            return CAPTURE;
        return MEDIC;
    }
}
