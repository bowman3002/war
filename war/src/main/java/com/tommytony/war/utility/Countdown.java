/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommytony.war.utility;

import com.tommytony.war.Warzone;
import com.tommytony.war.config.MonumentMode;
import com.tommytony.war.config.WarzoneConfig;
import com.tommytony.war.structure.Monument;

/**
 *
 * @author Austin
 */
public class Countdown implements Runnable
{
    Warzone warzone;
    Monument monument;
    int maxTime;
    int currentTime;
    
    public Countdown(Warzone w, Monument m)
    {
        warzone=w;
        monument=m;
        maxTime=warzone.getWarzoneConfig().getInt(WarzoneConfig.CAPTURETIME)/20;
        currentTime=maxTime;
    }
    
    public void run()
    {
        if(monument.getMode().equals(MonumentMode.CAPTURE))
            warzone.broadcast("Monument " + monument.getName() + " is " + currentTime + " seconds from locked for team " + monument.getOwnerTeam().getName());
        else if(monument.getMode().equals(MonumentMode.COMMAND))
        {
            if(currentTime==0)
            {
                reset();
            }
            warzone.broadcast("Monument " + monument.getName() + " is " + currentTime + " seconds from giving a point to " + monument.getOwnerTeam().getName());
        }
        currentTime-=5;
    }
    
    public void reset()
    {
        currentTime=maxTime;
    }
}
