package com.yecraft.commands;

import com.yecraft.bedwars.BedWars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;

public class CreateNPC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(BedWars.getInstance(),"trader", player.getLocation());
			npc.setSkin(
					"ewogICJ0aW1lc3RhbXAiIDogMTY4MTIxOTk2MDg0OCwKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMTdjMzc2YmNlOWJmOTgyNTc4MGM2YjczYjRiMjhiYjM1ZGExN2Y0NjIxY2Y4ZDgxNTZkZDk2ZTVkNzQ5MTMiCiAgICB9CiAgfQp9",
					"HKqTnqeOavHIr9lVKQLRhWEE4kQTpqpCP2ZTbiINbvH3eaP26H3ya6VFRz4SEqNvbGjJaRA0L/DwbPtTDdqT6O9wcIesGRhRtHx8X2KFejfvEXILCR8rTLIGawQ1Y+L6EhshDYzZHPSmHZJwG3tOwE9Ty/VOooPCgPpO2ctDFfa1moS5dfEHpqbQxiLE604fTrhHiszAa/XfCikkZ/nOEtv2uAnADMU7PxiTf79i3Eu7xMuNJaozdG1GP1NUod56Ve/rxUibdyfWZWlFkHyYwN/NJAhLVutQ/5RFOB5CMvWitlQcaYfOiFXafMIriOHT2hwmGZjkaYPcg9+NUk/q0me/v/A1oEuNQypiAd2IPVpgcPxA2XlQI0UyYXJs4KDuB40ig179+3Ik2WnO78Hq1/99dC8dnJqmijBxpLO3rr0PVM8pwIgyH+7hTkCoVEzWfGZJm3wvkKWfRyVJh2AE+bGuLP2iyjsf/qPfSwDxaLGRc72NlEidF+n5E6nTZCJccAegnTknuVPb+koIEAyAHmg3Aq52NqyK3C327C4x6GQCouZs7tCWAemtV8WDTTzRPaRwzqKJQz4ew8jfvblsG7KhSqcn3vzPtzvgKFupBYbliieoR6pdJLZsGIXdTXaEMJhTSCk6tkSaiGBYQ5ehXdczghO3sCLKmTw7un4lfYk="
			);
			npc.setCollidable(true);
			npc.setText("Торговець");
			npc.addRunPlayerCommandClickAction("/open");
		}
		return true;
	}
	
}