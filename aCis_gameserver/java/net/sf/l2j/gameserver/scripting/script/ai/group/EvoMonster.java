package net.sf.l2j.gameserver.scripting.script.ai.group;

import java.util.HashMap;
import java.util.Map;

import net.sf.l2j.gameserver.enums.EventHandler;
import net.sf.l2j.gameserver.model.actor.Creature;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.scripting.script.ai.AttackableAIScript;

public class EvoMonster extends AttackableAIScript
{
    private static final Map<Integer, Integer> MOBSTAGES = new HashMap<>(10);

    static
    {
        MOBSTAGES.put(1, 2);
        MOBSTAGES.put(2, 3);
        MOBSTAGES.put(3, 4);
        MOBSTAGES.put(4, 5);
        MOBSTAGES.put(5, 6);
        MOBSTAGES.put(6, 7);
        MOBSTAGES.put(7, 8);
        MOBSTAGES.put(8, 9);
        MOBSTAGES.put(9, 10);
    }

    public EvoMonster()
    {
        super("ai/group");
    }

    @Override
    protected void registerNpcs()
    {
        addEventIds(MOBSTAGES.keySet(), EventHandler.MY_DYING);
    }

    @Override
    public void onMyDying(Npc npc, Creature killer)
    {
        int nextStage = MOBSTAGES.getOrDefault(npc.getNpcId(), 0);
        if (nextStage > 0 && nextStage <= 10) {
            final Npc mob = addSpawn(nextStage, npc, false, 0, false);
            mob.forceAttack(killer, 200);
        }

        super.onMyDying(npc, killer);
    }
}
