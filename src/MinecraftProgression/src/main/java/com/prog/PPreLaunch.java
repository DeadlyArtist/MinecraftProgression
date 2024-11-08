package com.prog;

import com.prog.entity.attribute.PEntityAttributes;
import com.prog.event.EntityEvents;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class PPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        EntityEvents.CREATE_LIVING_ATTRIBUTES.register(builder -> PEntityAttributes.data.forEach((attribute, data) -> builder.add(attribute)));
    }
}
