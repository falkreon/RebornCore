package reborncore.shields.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import reborncore.shields.CustomShield;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Mark on 21/03/2016.
 */
public class RebornItemStackRenderer extends TileEntityItemStackRenderer
{

	private TileEntityBanner banner = new TileEntityBanner();
	private ModelShield modelShield = new ModelShield();

	private HashMap<String, FileSystemTexture> customTextureMap = new HashMap<>();

	TileEntityItemStackRenderer renderer;

	public RebornItemStackRenderer(TileEntityItemStackRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void renderByItem(ItemStack itemStackIn)
	{
		if (itemStackIn.getItem() == Items.SHIELD)
		{
			if (Items.SHIELD instanceof CustomShield)
			{
				CustomShield sheild = (CustomShield) itemStackIn.getItem();
				ResourceLocation location = sheild.getShieldTexture(itemStackIn);
				if (itemStackIn.getSubCompound("BlockEntityTag", false) != null)
				{
					banner.setItemValues(itemStackIn);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(BannerTextures.SHIELD_DESIGNS.getResourceLocation(
									banner.getPatternResourceLocation(), banner.getPatternList(),
									banner.getColorList()));
				} else if (location.getResourceDomain().equals("lookup"))
				{
					FileSystemTexture texture = null;
					if (customTextureMap.containsKey(location.getResourcePath()))
					{
						texture = customTextureMap.get(location.getResourcePath());
					} else
					{
						File file = null;
						for (File f : ShieldTextureLoader.instance.validFiles)
						{
							if (f.getName().replace(".png", "").equalsIgnoreCase(location.getResourcePath()))
							{
								file = f;
							}
						}
						if(file != null){
							texture = new FileSystemTexture(file);
							customTextureMap.put(location.getResourcePath(), texture);
						}
					}
					if(texture != null){
						Minecraft.getMinecraft().getTextureManager().loadTexture(location, texture);
						GlStateManager.bindTexture(texture.getGlTextureId());
					}
				} else
				{
					Minecraft.getMinecraft().getTextureManager().bindTexture(location);
				}
				GlStateManager.pushMatrix();
				GlStateManager.scale(1.0F, -1.0F, -1.0F);
				modelShield.render();
				GlStateManager.popMatrix();
				return;
			} else
			{
				renderer.renderByItem(itemStackIn);
			}
		} else
		{
			renderer.renderByItem(itemStackIn);
		}
	}
}