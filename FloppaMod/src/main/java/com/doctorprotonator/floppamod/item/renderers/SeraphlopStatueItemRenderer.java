package com.doctorprotonator.floppamod.item.renderers;

import com.doctorprotonator.floppamod.item.SeraphlopStatueItem;
import com.doctorprotonator.floppamod.item.models.SeraphlopStatueItemModel;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SeraphlopStatueItemRenderer extends GeoItemRenderer<SeraphlopStatueItem>
{
	public SeraphlopStatueItemRenderer()
	{
		super(new SeraphlopStatueItemModel());
	}
}