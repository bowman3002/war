/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tommytony.war.structure;

import com.tommytony.war.War;
import com.tommytony.war.Warzone;
import com.tommytony.war.config.InventoryBag;
import com.tommytony.war.config.TeamConfigBag;
import com.tommytony.war.config.TeamKind;
import com.tommytony.war.utility.Direction;
import com.tommytony.war.volume.BlockInfo;
import com.tommytony.war.volume.Volume;
import java.io.File;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 *
 * @author Bowman
 */
public class NeutralFlag {
    private Location loc = null;
    private String name;
    private Volume flagVolume;
    private Warzone warzone;
    private boolean stolen;
    
    
    public NeutralFlag(String name, Location flagLoc, Warzone warzone) {
        this.warzone = warzone;
        this.name = name;
        this.loc = flagLoc;
        setFlagVolume();
        initializeFlag();
        getFlagVolume().saveBlocks();
    }

    /**
     * @return the teamFlag
     */
    public Location getLocation() {
        return getLoc();
    }

    /**
     * @param teamFlag the teamFlag to set
     */
    public void setLocation(Location teamFlag) {
        this.setLoc(teamFlag);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the flagVolume
     */
    public Volume getFlagVolume() {
        return flagVolume;
    }

    /**
     * @param flagVolume the flagVolume to set
     */
    public void setFlagVolume(Volume flagVolume) {
        this.flagVolume = flagVolume;
    }

    /**
     * @return the warzone
     */
    public Warzone getWarzone() {
        return warzone;
    }

    /**
     * @param warzone the warzone to set
     */
    public void setWarzone(Warzone warzone) {
        this.warzone = warzone;
    }
    
    private void setFlagVolume() {
		if (this.flagVolume == null) {
			this.flagVolume = new Volume(this.getName(), this.warzone.getWorld());
		}
		if (this.flagVolume.isSaved()) {
			this.flagVolume.resetBlocks();
		}
		int x = this.getLoc().getBlockX();
		int y = this.getLoc().getBlockY();
		int z = this.getLoc().getBlockZ();
		this.flagVolume.setCornerOne(this.warzone.getWorld().getBlockAt(x - 1, y - 1, z - 1));
		this.flagVolume.setCornerTwo(this.warzone.getWorld().getBlockAt(x + 1, y + 3, z + 1));
	}

	@SuppressWarnings("unused")
	public final void initializeFlag() {
		// make air (old two-high above floor)
		Volume airGap = new Volume("airgap", this.warzone.getWorld());
		airGap.setCornerOne(new BlockInfo(
				this.flagVolume.getCornerOne().getX(), 
				this.flagVolume.getCornerOne().getY() + 1, 
				this.flagVolume.getCornerOne().getZ(),
				0,
				(byte)0));
		airGap.setCornerTwo(new BlockInfo(
				this.flagVolume.getCornerTwo().getX(), 
				this.flagVolume.getCornerOne().getY() + 2, 
				this.flagVolume.getCornerTwo().getZ(),
				0,
				(byte)0));
		airGap.setToMaterial(Material.AIR);

		// Set the flag blocks
		int x = this.getLoc().getBlockX();
		int y = this.getLoc().getBlockY();
		int z = this.getLoc().getBlockZ();
		
		Material main = Material.getMaterial(this.warzone.getWarzoneMaterials().getMainId());
		byte mainData = this.warzone.getWarzoneMaterials().getMainData();
		Material stand = Material.getMaterial(this.warzone.getWarzoneMaterials().getStandId());
		byte standData = this.warzone.getWarzoneMaterials().getStandData();
		Material light = Material.getMaterial(this.warzone.getWarzoneMaterials().getLightId());
		byte lightData = this.warzone.getWarzoneMaterials().getLightData();

		// first ring
		Block current = this.warzone.getWorld().getBlockAt(x + 1, y - 1, z + 1);
		current.setType(main);
		current.setData(mainData);
		current = this.warzone.getWorld().getBlockAt(x + 1, y - 1, z);
		current.setType(main);
		current.setData(mainData);
		current = this.warzone.getWorld().getBlockAt(x + 1, y - 1, z - 1);
		current.setType(main);
		current.setData(mainData);
		current = this.warzone.getWorld().getBlockAt(x, y - 1, z + 1);
		current.setType(main);
		current.setData(mainData);
		current = this.warzone.getWorld().getBlockAt(x, y - 1, z);
		current.setType(light);
		current.setData(lightData);
		current = this.warzone.getWorld().getBlockAt(x, y - 1, z - 1);
		current.setType(main);
		current.setData(mainData);
		current = this.warzone.getWorld().getBlockAt(x - 1, y - 1, z + 1);
		current.setType(main);
		current.setData(mainData);
		current = this.warzone.getWorld().getBlockAt(x - 1, y - 1, z);
		current.setType(main);
		current.setData(mainData);
		current = this.warzone.getWorld().getBlockAt(x - 1, y - 1, z - 1);
		current.setType(main);
		current.setData(mainData);

		// flag
		this.warzone.getWorld().getBlockAt(x, y + 1, z).setType(Material.WOOL);
		this.warzone.getWorld().getBlockAt(x, y + 1, z).setData((byte) 0);

		// Flag post using Orientation
		int yaw = 0;
		if (this.getLoc().getYaw() >= 0) {
			yaw = (int) (this.getLoc().getYaw() % 360);
		} else {
			yaw = (int) (360 + (this.getLoc().getYaw() % 360));
		}
		BlockFace facing = null;
		BlockFace opposite = null;
		if ((yaw >= 0 && yaw < 45) || (yaw >= 315 && yaw <= 360)) {
			facing = Direction.WEST();
			opposite = Direction.EAST();
			current = this.warzone.getWorld().getBlockAt(x, y, z - 1);
			current.setType(stand);
			current.setData(standData);
			current = this.warzone.getWorld().getBlockAt(x, y + 1, z - 1);
			current.setType(stand);
			current.setData(standData);
		} else if (yaw >= 45 && yaw < 135) {
			facing = Direction.NORTH();
			opposite = Direction.SOUTH();
			current = this.warzone.getWorld().getBlockAt(x + 1, y, z);
			current.setType(stand);
			current.setData(standData);
			current = this.warzone.getWorld().getBlockAt(x + 1, y + 1, z);
			current.setType(stand);
			current.setData(standData);
		} else if (yaw >= 135 && yaw < 225) {
			facing = Direction.EAST();
			opposite = Direction.WEST();
			current = this.warzone.getWorld().getBlockAt(x, y, z + 1);
			current.setType(stand);
			current.setData(standData);
			current = this.warzone.getWorld().getBlockAt(x, y + 1, z + 1);
			current.setType(stand);
			current.setData(standData);;
		} else if (yaw >= 225 && yaw < 315) {
			facing = Direction.SOUTH();
			opposite = Direction.NORTH();
			current = this.warzone.getWorld().getBlockAt(x - 1, y, z);
			current.setType(stand);
			current.setData(standData);
			current = this.warzone.getWorld().getBlockAt(x - 1, y + 1, z);
			current.setType(stand);
			current.setData(standData);
		}
	}
        
        public final void resetFlag(Location flagLoc) {
            this.setLoc(flagLoc);
            setFlagVolume();
            initializeFlag();
        }

    public void deleteFlag() {
        this.getFlagVolume().resetBlocks();
        this.setFlagVolume(null);
        this.setLoc(null);

        // remove volume file
        String filePath = War.war.getDataFolder().getPath() + "/dat/warzone-" + this.warzone.getName() + "/volume-" + this.getName() + "flag.dat";
        if (!new File(filePath).delete()) {			
                War.war.log("Failed to delete file " + filePath, Level.WARNING);
        }
    }

    /**
     * @return the stolen
     */
    public boolean isStolen() {
        return stolen;
    }

    /**
     * @param stolen the stolen to set
     */
    public void setStolen(boolean stolen) {
        this.stolen = stolen;
    }

    /**
     * @return the loc
     */
    public Location getLoc() {
        return loc;
    }

    /**
     * @param loc the loc to set
     */
    public void setLoc(Location loc) {
        this.loc = loc;
    }
    
    public boolean isFlagBlock(Block block) {
        if (this.loc != null) {
                int flagX = this.loc.getBlockX();
                int flagY = this.loc.getBlockY() + 1;
                int flagZ = this.loc.getBlockZ();
                if (block.getX() == flagX && block.getY() == flagY && block.getZ() == flagZ) {
                        return true;
                }
        }
        return false;
    }
}
