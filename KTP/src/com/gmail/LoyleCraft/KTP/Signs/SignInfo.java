package com.gmail.LoyleCraft.KTP.Signs;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class SignInfo {
	  private String worldname;
	  private int x;
	  private int y;
	  private int z;
	  
	  public SignInfo(String worldname, int x, int y, int z)
	  {
	    this.worldname = worldname;
	    this.x = x;
	    this.y = y;
	    this.z = z;
	  }
	  
	  public SignInfo(Block block)
	  {
	    this.worldname = block.getWorld().getName();
	    this.x = block.getX();
	    this.y = block.getY();
	    this.z = block.getZ();
	  }
	  
	  public Block getBlock()
	  {
	    if (this.worldname == null) {
	      return null;
	    }
	    World world = Bukkit.getWorld(this.worldname);
	    if ((world != null) && 
	      (world.isChunkLoaded(this.x >> 4, this.z >> 4))) {
	      return world.getBlockAt(this.x, this.y, this.z);
	    }
	    return null;
	  }
	  
	  protected String getWorldName()
	  {
	    return this.worldname;
	  }
	  
	  protected int getX()
	  {
	    return this.x;
	  }
	  
	  protected int getY()
	  {
	    return this.y;
	  }
	  
	  protected int getZ()
	  {
	    return this.z;
	  }
	  
	  public int hashCode()
	  {
	    return this.worldname.hashCode() ^ this.x ^ this.y ^ this.z;
	  }
	  
	  public boolean equals(Object obj)
	  {
	    if (!(obj instanceof SignInfo)) {
	      return super.equals(obj);
	    }
	    SignInfo si = (SignInfo)obj;
	    return (this.worldname.equals(si.worldname)) && (this.x == si.x) && (this.y == si.y) && (this.z == si.z);
	  }
	}
