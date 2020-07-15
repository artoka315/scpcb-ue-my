; ~ [OPTIONS]

Const OptionFile$ = "Data\options.ini"

; ~ [VERSION]

Const VersionNumber$ = "1.0.0"

; ~ [OBJECTS]

Const MaxMTModelIDAmount% = 7

Const MaxMonitorModelIDAmount% = 3

Const MaxDoorModelIDAmount% = 11

Const MaxButtonModelIDAmount% = 5

Const MaxLeverModelIDAmount% = 2

Const MaxCamModelIDAmount% = 2

Const MaxMiscModelIDAmount% = 1

Const MaxNPCModelIDAmount% = 34

; ~ [NPCs]

Const NPCsFile$ = "Data\NPCs.ini"

Const NPCtype008_1% = 1, NPCtype035_Tentacle% = 2, NPCtype049% = 3, NPCtype049_2% = 4, NPCtype066% = 5, NPCtype096% = 6
Const NPCtype106% = 7, NPCtype173% = 8, NPCtype372% = 9, NPCtype513_1% = 10, NPCtype860_2% = 11, NPCtype939% = 12
Const NPCtype966% = 13, NPCtype1499_1% = 14

Const NPCtypeApache% = 15, NPCtypeClerk% = 16, NPCtypeD% = 17, NPCtypeGuard% = 18, NPCtypeMTF% = 19, NPCtypeVehicle% = 20

; ~ [FOREST GENERATION]

Const GridSize% = 10
Const Deviation_Chance% = 40 ; ~ Out of 100
Const Branch_Chance% = 65
Const Branch_Max_Life% = 4
Const Branch_Die_Chance% = 18
Const Max_Deviation_Distance% = 3
Const Return_Chance% = 27
Const Center% = 5

; ~ [MAIN MENU TABS]

Const MainMenuTab_Default% = 0
Const MainMenuTab_New_Game% = 1
Const MainMenuTab_Load_Game% = 2
Const MainMenuTab_Options_Graphics% = 3
Const MainMenuTab_Load_Map% = 4
Const MainMenuTab_Options_Audio% = 5
Const MainMenuTab_Options_Controls% = 6
Const MainMenuTab_Options_Advanced% = 7

; ~ [FOG]

Const FogColorLCZ$ = "010010010"
Const FogColorHCZ$ = "010006006"
Const FogColorEZ$ = "010010020"
Const FogColorStorageTunnels$ = "005015003"
Const FogColorOutside$ = "255255255"
Const FogColorDimension1499$ = "096097104"
Const FogColorPD$ = "000000000"
Const FogColorPDTrench$ = "038055047"
Const FogColorForest$ = "098133162"

; ~ [INVENTORY SIZE]

Const INVENTORY_GFX_SIZE% = 70
Const INVENTORY_GFX_SPACING% = 35

; ~ [MAP]

Const RoomsFile$ = "Data\rooms.ini"

Const MaxRoomLights% = 32
Const MaxRoomEmitters% = 8
Const MaxRoomObjects% = 30

Const ROOM1% = 1
Const ROOM2% = 2
Const ROOM2C% = 3
Const ROOM3% = 4
Const ROOM4% = 5

Const GridSZ% = 19 ; ~ Same size as the main map itself (better for the map creator)

Const RoomScale# = 8.0 / 2048.0

; ~ [COLLISIONS]

Const HIT_MAP% = 1
Const HIT_PLAYER% = 2
Const HIT_ITEM% = 3
Const HIT_APACHE% = 4
Const HIT_178% = 5
Const HIT_DEAD% = 6

; ~ [TEXTURES]

Const MaxDTextures% = 16

Const MaxMiscTextureIDAmount% = 18

Const MaxMonitorTextureIDAmount% = 5

Const MaxOverlayTextureIDAmount% = 12
Const MaxOverlayIDAmount% = 12

Const MaxDecalTextureIDAmount% = 20

Const MaxParticleTextureIDAmount% = 9

Const MaxLightSpriteIDAmount% = 3

Const MaxIconIDAmount% = 6
Const MaxImageIDAmount% = 13

; ~ [ITEMS]

Const ItemsPath$ = "GFX\items\"

Const MaxItemAmount% = 10

Const NAV_WIDTH% = 287
Const NAV_HEIGHT% = 256

; ~ [DIFFICULTY]

Const SAFE% = 0
Const EUCLID% = 1
Const KETER% = 2
Const ESOTERIC% = 3

Const SAVEANYWHERE% = 0
Const SAVEONQUIT% = 1
Const SAVEONSCREENS% = 2

Const EASY% = 0
Const NORMAL% = 1
Const HARD% = 2

; ~ [ACHIEVEMENTS]

Const AchievementsFile$ = "Data\achievements.ini"

Const MAXACHIEVEMENTS% = 41

Const Achv005% = 0, Achv008% = 1, Achv012% = 2, Achv035% = 3, Achv049% = 4, Achv055% = 5,  Achv079% = 6, Achv096% = 7, Achv106% = 8
Const Achv148% = 9, Achv205% = 10, Achv294% = 11, Achv372% = 12, Achv409% = 13, Achv420J% = 14, Achv427% = 15, Achv500% = 16, Achv513% = 17
Const Achv714% = 18, Achv789J% = 19, Achv860% = 20, Achv895% = 21, Achv914% = 22, Achv939% = 23, Achv966% = 24, Achv970% = 25
Const Achv1025% = 26, Achv1048% = 27, Achv1123% = 28, Achv1162% = 29, Achv1499% = 30

Const AchvConsole% = 31, AchvHarp% = 32, AchvKeter% = 33, AchvKeyCard6% = 34, AchvMaynard% = 35, AchvOmni% = 36
Const AchvO5% = 37, AchvPD% = 38, AchvSNAV% = 39, AchvTesla% = 40

; ~ [FONTS]

Const MaxFontIDAmount% = 5

Const MaxCreditsFontIDAmount% = 2

; ~ [PLAYER]

Const SubjectName$ = "Subject D-9341"

; ~ [LAUNCHER]

Const LauncherWidth% = 640
Const LauncherHeight% = 480

; ~ [FPS]

Const TICK_DURATION# = 70.0 / 60.0

; ~ [SOUNDS]

Const Mode% = 2
Const TwoD% = 8192

; ~ [MATERIALS]

Const MaterialsFile$ = "Data\materials.ini"

; ~ [LOADING SCREENS]

Const LoadingScreensFile$ = "LoadingScreens\loading_screens.ini"

;~IDEal Editor Parameters:
;~C#Blitz3D