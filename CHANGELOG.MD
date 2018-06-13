# Changelog

## 1.9.0

- Modified ProjectE support to lock transmutation learning and condenser multiplication.
  - The syntax is the same as before `mods.compatskills.EMCLock.addEMCLock(int emc, String... requirements);`
  - Players will be able to use items that do not meet the emc lock but will not be able to learn or duplicate that item. Learning and duplication now also requires the player to meet any requirements the item has on it.
  - *Note*: For putting items in the transmutation table you will still gain the emc but will not be able to get the item back out.
- Bump minimum Reskillable version to 1.8.0 for use of the tooltip injection.


## 1.8.0

- Some internal code cleanup and small performance improvements.
- Added Harvest Level locking and harvest requirements. Both of these lock/require the given level and up.
  - You can lock based on the block's required harvest level or the harvest level of tools. Examples:
    - mods.compatskills.HarvestLock.addToolLevelLock(1, "reskillable:mining|3");
    - mods.compatskills.HarvestLock.addBlockLevelLock(2, "reskillable:mining|7");
  - Requirement format:
    - harvest|integer
- Support for Reskillable's new Requirement Caching. Increases performance and memory usage a lot. Also uses the new tooltip calculation technique in Reskillable so that tooltips can be retrieved quicker.
- Fixed GameStageUnlockables not syncing the fact that the game stage was added.
- Utilize the new RequirementException from Reskillable so that invalid requirement strings can give a better and more detailed error message.



## 1.7.1

- Fixes the accidental removal of dimension requirements. 


## 1.7.0

- Added ru_RU localization
  - Thanks goes out to: Pirog17000
- Added OreDictionary Locking and Requirements
  - Locking
    - mods.compatskills.OreDictLock.addOreDictLock(<ore:ingotIron>, "reskillable:building|15");
    - mods.compatskills.OreDictLock.addNBTOreDictLock(<ore:ingotGold>, {ench:[{lvl:1 as short, id: 17 as short}]}, "reskillable:building|17");
  - Requirements
    - ore|ingotIron
    - ore|ingotGold|{ench:[{lvl:1s, id: 17s}]}
- Changes Custom Traits and Dummy Traits (GameStages) to allow setting of custom resource locations for the icons of custom traits and dummy traits. (This allows setting the icons to existing images).
- Replaced internal logic for inverted requirements with using the new NOT requirement logic in Reskillable.I
  - It is recommended to replace your scripts to have "not|requirement" instead of having "!requirement"
    - For example instead of "!dim|-1" you would use "not|dim|-1".
  - Eventually the inverted requirement format may be removed which is why it is recommended to switch over.
- Some internal cleanup of code and using some new API features of Reskillable



## 1.6.0

- Added Tile-Entity Locking
  - mods.compatskills.TileEntityLock.addTileEntityLock("minecraft:furnace", "reskillable:defense|5");
- Added ItemStack "Holding" Requirement
  - This requires the player to hold the required item in either hand.
    - "stack|minecraft:iron_sword:*|{ench:[{id: 17s}]}"
    - "stack|minecraft:iron_pickaxe:*"
    - "stack|minecraft");
    - "stack||{ench:[{id: 33s}]}"); //Needs a silk touch item in hand can be offhand
    - "stack|tconstruct|{ench:[{id: 35s}]}");//fortune tinkers tool
- Added "Attack" Locking for Entities
  - This means the player can't attack Entities of specific kind without meeting all the requirements.
    - (Yes, We implemented this)
      - Why you ask?
        - Why not?
          - We're not insane I promise!
            - Well.....
              - That's a story for another time ^^
- Made some improvements to GameStage Unlockables:
  - You can now specify the name and description in a similar manner to Custom Traits.
  - Changes made to the GameStage Unlockable in ZenScript now properly updates the in-game trait upon restart.
- Made some changes to Requirements "getTooltip" override to be safer with Null PlayerData.
- Made some changes to GameStagesUnlockable and Custom Traits to allow for no set requirements.



## 1.5.0

- Added zh_cn.lang
    - Thanks go out to SihenZhang on Github for the PR
    - Some changes have been done internally for the localization strings.
      - We will reach out to him about these changes!
- Added Dimension Support:
  - DimensionChangedEvent
    - You can now lock the ability to travel to certain dimensions!
  - Dimension Requirement
    - Standard and Inverted requirements have been added
      - dim|integer
      - !dim|integer
    - The Normal Dimension requirement requires you to be IN the specified dimension.
    - The Inverted Dimension requirement requires you to be in ANY dimension OTHER than the specified dimension.
- Added Entity Support
  - AnimalTameEvent
    - You can now lock the ability to Tame specified Entities
- EntityMountEvent
  - You can now lock the ability to Mount specified Entities
- Added ProjectE Support
  - You can now lock Items based on EMC
    - It checks "fuzzily" which means it checks the cost or higher in this case.
- Added Tinker's Construct Support!
  - PSA:
    - Older Versions of Tinker's Construct (Older than 2.10.0.XX, Last tested Build that didn't work: 2.10.0.77)
      - (In Regards to Tool Crafting) 
      - Doesn't update the GUI properly upon initial opening and closing of the GUI.
      - This means that the Event will be properly canceled and thus not output a crafted item.
      - However, it will also not update the GUI and thus not display the Error Text.
      - This is resolved simply by interacting with the GUI like pulling out and adding back a tool part.
  - You can now Lock Materials
    - This disables the following features unless the requirements are met for the material:
      - Part Replacement 
      - Part Crafting
      - Tool Crafting
  - You can now lock Modifiers:
    - This disables the following features unless the requirements are met for the Modifier:
      - Part Crafting (Applies if the Material has the specified Modifier/Trait)
      - Tool Modification
  - You can now Lock Crafting of Specific Tools
    - This is done by just applying a simple ItemStack Lock to the base tool item.
- Added a few Setters/Getters/Methods for Custom Skills/Traits
  - Skills:
    - setLevelCap/getLevelCap
    - setEnabled/getEnabled
    - setSkillPointInterval
    - setBaseLevelCost/getBaseLevelCost
    - getName
  - Traits:
    - setEnabled/getEnabled
    - getName
    - getDescription
- Implemented a fix for Custom Skills/Traits
  - Disclaimer:
    - This only works for Custom Skills/Traits (Duh..)
  - TLDR:
    - This makes it so if you update some of the information in the ZenScript the Skill/Trait will now properly update.
  - Long Story
    - Previously when we tried to edit information post-fact we were essentially passing a new Skill object to our Skill Registry.
    - This got denied since there was already an existing Skill Object with the resource-location string.
    - Now if an object already exists, we reference the object, change the appropriate information and return it to the registry as a new object.
- Implemented a fix in both Reskillable and CompatSkills
  - This fix locks not working on Server-Side properly (Whoops....)
- One of the Blood Magic Ritual-Locks have been changed:
  - The Ritual Cost-lock have been changed to now lock Rituals with a:
    - `>=` instead of a = check.
    - This means that it will now lock anything that has the specified amount of higher!
    - Instead of just the specified cost.



## 1.4.1

- Fixed issue due to package changes


## 1.4.0

- Added Inverted Requirements
  - Inverted Advancement
    - Syntax:
      - !adv|
      - Is unlocked if you do not have the specified Advancement
  - Inverted GameStage
    - Syntax
      - !stage|
      - Is unlocked if you do not have the specified Stage
  - Inverted Skill
    - Syntax
      - !skill|
      - Is unlocked if you do not have the specified Skill-level or Higher
  - Inverted Trait
    - Syntax
      - !trait|
      - Is unlocked if you do not have the specified Trait unlocked
- Added new NBT-Locking Capabilities
  - You can now lock individual NBT-tags OR individual NBT-tags on a per mod basis!
    - Syntax
      - mods.compatskills.NBTLock.addGenericNBTLock({ench:[{id: 33 as short}]}, "reskillable:magic|10");
      - mods.compatskills.NBTLock.addModNBTLock("minecraft", {ench:[{id: 34 as short}]}, "reskillable:gathering|6");
- Moved all GameStage Support over to the GameStages 2.0.0 API
- Added GameStage Locking
  - Syntax
    - mods.compatskills.GameStageLocks.addGameStageLock(String gamestage, String... defaultRequirements);
    - mods.compatskills.GameStageLocks.addGameStageLock("age1", "reskillable:building|15", "reskillable:magic|7", "adv|minecraft:husbandry/plant_seed", "stage|test");
  - This works similarly to my other Locks
    - In this case once a GameStage is unlocked, it runs through 2 Event Stages.
      - Add and Added.
      - With this support once the GameStage enters "Add" it'll check if that game stage has a "lock" attached to it.
      - If it does and the requirements for the lock aren't met, it'll cancel the event and send an error status message to the player.
- Reworked Blood Magic Support
  - Rituals no longer takes a Failure Message String
    - Instead this Failure Message now uses a Localizable String
  - Added 2 new kinds of Ritual Locks to make 3 total:
    - New Syntaxes:
      - mods.compatskills.RitualHandler.addRitualLock(String ritual, String... requirements);
      - mods.compatskills.RitualHandler.addRitualCostLock(int activationCost, String... requirements);
      - mods.compatskills.RitualHandler.addRitualCrystalLock(int crystalLevel, String... requirements);
  - The Binding Lock has been removed and now has instead been added as a property of normal item-locking!
    - Test Example:
      - Does All Blood Orbs
        - mods.compatskills.Requirement.addRequirement(<bloodmagic:blood_orb>, "reskillable:magic|5");
      - Does The Weak Blood Orb
        - mods.compatskills.Requirement.addRequirement(<bloodmagic:blood_orb>.withTag({orb: "bloodmagic:weak"}), "reskillable:magic|5"); 
- Finished Implementing the Skill Level-Lock MechanicPlease do note that mods that call the "levelUp();" method directly will bypass the lock!
  - Updated most if not all methods to now use the new internal methods for Requirements and Locking
- Reworked the following Supports to load later:
  - Blood Magic 
    - Binding Support
    - Ritual Support
  - Immersive Engineering
    - Multiblock Support
  - Magneticraft
    - Multiblock Support


## 1.3.0

- Added Baubles Support
- Added Hwyla/Waila Support
- Added TheOneProbe(TOP) Support
- Added a Mod-locking Tweaker for CraftTweaker
  - mods.compatskills.ModLock.addModLock(String modId, String... Locked);
- Features past this point are WIP and are not currently functioning but are still implemented Code-wise!
- Added the ability to lock Skill-Levels behind Requirements
  - Script Syntax:
    - mods.compatskills.SkillLocks.addLevelLock(CTSkill skill, int level, String... defaultRequirements);
    - mods.compatskills.SkillLocks.addLevelLock(<skill:reskillable:agility>, 5, "reskillable:building|15", "reskillable:magic|7", "adv|minecraft:husbandry/plant_seed", "stage|test");
  - This means that if you gate level 10 of magic behind:
    - Farming 4
    - Attack 8
  - If you try and level-up from 9 > 10
    - If you do not meet the requirements it'll cancel the level-up and throw an error message!



## 1.2.1

- Fixes a crash with Blood Magic!
  - Thanks @TehNut for helping Debug


## 1.2.0

- Added A LOT of debugging code to all the supported ZenMethods in CompatSkills
- Added Skill and Unlockable Bracket Handlers
	- Skill
		- <skill:modid:skilltarget>
		- <skill:reskillable:attack>
	- Unlockable
		- <unlockable:modid:unlockableTarget>
		- <unlockable:reskillable:battlespirit>
- Added ZenExpansions for Skills, PlayerData, PlayerSkillInfo, Unlockables
- Added the ability to create Custom Skills and Traits using Zenscript
	- Skills
		- mods.compatskills.SkillCreator
			- createSkill(String name, String backGroundLocation)
				- name is just a name, the mod id for the resource location is auto-provided as "compatskills"
				- backGroundLocation is a resourceLocation for the texture:
					- "textures/blocks/stonebrick.png"
			- createNewSkill(String nameLocation, String backGroundLocation)
				- nameLocation does not have a pre-provided ModId for the ResourceLocation
					- It is highly suggested you provide a "ModId"
						- IE "bananapancakes:supercoolskill"
				- backGroundLocation
					- Same as Above
	- Traits
		- mods.compatskills.TraitCreator
			// Mod ID Provided
			- createTrait(String traitName, int x, int y, String skillLocation, int cost, String... requirements)
				- createTrait("banana", 1, 1, "reskillable:attack", 5, "reskillable:building|15", "reskillable:magic|7", "adv|minecraft:husbandry/plant_seed", "stage|test")
			- createTrait(String traitName, int x, int y, CrTSkill parentSkill, int cost, String... requirements)
				- createTrait("banana", 1, 1, <skill:reskillable:attack>, 5, "reskillable:building|15", "reskillable:magic|7", "adv|minecraft:husbandry/plant_seed", "stage|test")
			This is the same as Above, however the Trait Location does not provide a standard modid, similar to "createNewSkill"{
				// Not Provided Mod ID
				- createNewTrait("banana", 1, 1, "reskillable:attack", 5, "reskillable:building|15", "reskillable:magic|7", "adv|minecraft:husbandry/plant_seed", "stage|test")
					- createNewTrait(String traitLocation, int x, int y, String skillLocation, int cost, String... requirements)
				- createNewTrait("banana:pancake", 1, 1, <skill:reskillable:attack>, 5, "reskillable:building|15", "reskillable:magic|7", "adv|minecraft:husbandry/plant_seed", "stage|test")
					- createNewTrait(String traitLocation, int x, int y, CrTSkill parentSkill, int cost, String... requirements)
			}
- RequirementTweaker for CrT Requirement Locking now uses VarArgs just like every other ZenMethod in this mod!
	- This means it changes from:
		- mods.compatskills.Requirement.addRequirement(<minecraft:wooden_pickaxe:*>, "reskillable:defense|5,reskillable:magic|5,reskillable:agility|5");
	- To:
		- mods.compatskills.Requirement.addRequirement(<minecraft:wooden_pickaxe:*>, "reskillable:defense|5", "reskillable:magic|5", "reskillable:agility|5");
    


## 1.1.0

- Added Blood Magic-Support 
    - This is WIP but should work 
    - Please report any issues you might find!
- Added Magneticraft-Support 
    - This is WIP but should work 
    - Please report any issues you might find!
- Updated the CrT support to now match the new lock system in Reskillable.
- Updated the GameStages Support to make GameStages traits to unlock traits properly. 
    - This currently triggers the in-game command.
- Fixed Trait Requirements crashing when referencing GameStages Traits.



## 1.0.0

- Added Crafttweaker Support for Requirement Locking!
    - Syntax: 
        - mods.reskillable.Requirement.addRequirement(IItemStack item, String locked);
            - IItemStack is the item being locked!
            - The String is the same format as a config lock, and defines what locks the item!
    - Example:
        - mods.reskillable.Requirement.addRequirement(<minecraft:stick>, "reskillable:mining|7");
        - mods.reskillable.Requirement.addRequirement(<minecraft:wooden_pickaxe:*>.withTag({ench: [{lvl: 1 as short, id: 33 as short}]}), "reskillable:mining|5");
    - CrT Requirement Locking has Added NBT Support.

- Added GameStages Support!
	- Added the ability to use GameStages as Requirements
		- https://i.imgur.com/oc2cxxk.png
	- Added the ability to create "Dummy Unlockables" that are tied to a gamestage and will unlock it upon purchase
		- https://i.imgur.com/KL05IRq.png
		- mods.compatskills.GameStageUnlockable.addGameStageUnlockable(String gamestage, String name, int xPos, int yPos, String parentSkill, int skillpointCost, String... requirements);
		- mods.compatskills.GameStageUnlockable.addGameStageUnlockable("a", "a", 0, 0, "reskillable:gathering", 3, "stage|test");
	- "Dummy Unlockables" needs to have their icon added.
		- This is usually done through something like BASE or Resource Loader as well!
			- The path is:
				- "name" = name of specified in the CrT name
				- reskillable/textures/unlockables/name.jpg
	- "Dummy Unlockables" needs to have their Name and Descriptions localized manually using .lang files
		- This can be accomplished using BASE or Resource Loader!

- Added Immersive Engineering Multiblock Support!
	- You can now lock IE Multiblock's Behind Requirements!
		- mods.compatskills.IEMultiBlockGate.addGate(String multiblock, String errorMessage, String... requirements);
		- mods.compatskills.IEMultiBlockGate.addGate("IE:DieselGenerator", "I'm afraid this is just too complex for an idiot like you!", "reskillable:building|15", "reskillable:magic|7", "stage|test", "adv|minecraft:husbandry/plant_seed");
