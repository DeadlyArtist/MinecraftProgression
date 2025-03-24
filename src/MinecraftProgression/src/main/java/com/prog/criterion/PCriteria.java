package com.prog.criterion;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

public class PCriteria {
    public static final DefeatRankCriterion DEFEAT_RANK = register(new DefeatRankCriterion());
    public static final AnvilCriterion ANVIL = register(new AnvilCriterion());

    public static <T extends Criterion<?>> T register(T object) {
        return Criteria.register(object);
    }

    public static void init() {
        LOGGER.info("Registering Criteria for: " + Prog.MOD_ID);
    }
}
