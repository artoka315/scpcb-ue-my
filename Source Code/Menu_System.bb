Global MenuBack% = LoadImage_Strict("GFX\menu\back.png")
Global MenuText% = LoadImage_Strict("GFX\menu\SCP_text.png")
Global Menu173% = LoadImage_Strict("GFX\menu\scp_173_back.png")
MenuWhite = LoadImage_Strict("GFX\menu\menu_white.png")
MenuBlack = LoadImage_Strict("GFX\menu\menu_black.png")
MaskImage(MenuBlack, 255, 255, 0)

ResizeImage(MenuBack, ImageWidth(MenuBack) * MenuScale, ImageHeight(MenuBack) * MenuScale)
ResizeImage(MenuText, ImageWidth(MenuText) * MenuScale, ImageHeight(MenuText) * MenuScale)
ResizeImage(Menu173, ImageWidth(Menu173) * MenuScale, ImageHeight(Menu173) * MenuScale)
ResizeImage(QuickLoadIcon, ImageWidth(QuickLoadIcon) * MenuScale, ImageHeight(QuickLoadIcon) * MenuScale)

For i = 0 To 3
	ArrowIMG[i] = LoadImage_Strict("GFX\menu\arrow.png")
	RotateImage(ArrowIMG[i], 90.0 * i)
	HandleImage(ArrowIMG[i], 0, 0)
Next

Global RandomSeed$

Global MenuBlinkTimer%[2], MenuBlinkDuration%[2]
MenuBlinkTimer[0] = 1
MenuBlinkTimer[1] = 1

Global MenuStr$, MenuStrX%, MenuStrY%

Global MainMenuTab%, PrevMainMenuTab%, ShouldDeleteGadgets%

Global SelectedInputBox%, CursorPos% = -1

Const SavePath$ = "Saves\"
Global SaveMSG$

Global CurrSave$
Global SaveGameAmount%

Dim SaveGames$(SaveGameAmount + 1) 
Dim SaveGameTime$(SaveGameAmount + 1)
Dim SaveGameDate$(SaveGameAmount + 1)
Dim SaveGameVersion$(SaveGameAmount + 1)

Global SavedMapsAmount% = 0
Dim SavedMaps$(SavedMapsAmount + 1)
Dim SavedMapsAuthor$(SavedMapsAmount + 1)

Global SelectedMap$

LoadSaveGames()

Global CurrLoadGamePage% = 0

Const VersionNumber$ = "1.0.0"

; ~ Menu Tabs Constants
;[Block]
Const MainMenuTab_Default% = 0
Const MainMenuTab_New_Game% = 1
Const MainMenuTab_Load_Game% = 2
Const MainMenuTab_Options_Graphics% = 3
Const MainMenuTab_Load_Map% = 4
Const MainMenuTab_Options_Audio% = 5
Const MainMenuTab_Options_Controls% = 6
Const MainMenuTab_Options_Advanced% = 7
;[End Block]

Function UpdateMainMenu()
	Local x%, y%, Width%, Height%, Temp%, i%, n%, j%
	Local Dir%, File$, Test%, snd.Sound
	
	While ft\Accumulator > 0.0
		ft\Accumulator = ft\Accumulator - GetTickDuration()
		If ft\Accumulator =< 0.0 Then CaptureWorld()
		
		If Input_ResetTime > 0.0 Then
			Input_ResetTime = Max(Input_ResetTime - fpst\FPSFactor[0], 0.0)
		Else
			DoubleClick = False
			MouseHit1 = MouseHit(1)
			If MouseHit1 Then
				If MilliSecs2() - LastMouseHit1 < 800 Then DoubleClick = True
				LastMouseHit1 = MilliSecs2()
			EndIf
			
			Local PrevMouseDown1% = MouseDown1
			
			MouseDown1 = MouseDown(1)
			If PrevMouseDown1 = True And (Not MouseDown1) Then MouseUp1 = True Else MouseUp1 = False
			
			MouseHit2 = MouseHit(2)
		EndIf
		
		If (Not MouseDown1) And (Not MouseHit1) Then GrabbedEntity = 0
		
		UpdateMusic()
		If EnableSFXRelease Then AutoReleaseSounds()
		
		If ShouldPlay = 21 Then
			EndBreathSFX = LoadSound_Strict("SFX\Ending\MenuBreath.ogg")
			EndBreathCHN = PlaySound_Strict(EndBreathSFX)
			ShouldPlay = 66
		ElseIf ShouldPlay = 66
			If (Not ChannelPlaying(EndBreathCHN)) Then
				FreeSound_Strict(EndBreathSFX)
				ShouldPlay = 11
			EndIf
		Else
			ShouldPlay = 11
		EndIf
		
		If Rand(300) = 1 Then
			MenuBlinkTimer[0] = Rand(4000, 8000)
			MenuBlinkDuration[0] = Rand(200, 500)
		EndIf
		
		MenuBlinkTimer[1] = MenuBlinkTimer[1] - fpst\FPSFactor[0]
		If MenuBlinkTimer[1] < MenuBlinkDuration[1] Then
			If MenuBlinkTimer[1] < 0 Then
				MenuBlinkTimer[1] = Rand(700, 800)
				MenuBlinkDuration[1] = Rand(10, 35)
			EndIf
		EndIf
		
		If (Not MouseDown1) Then OnSliderID = 0
		
		If PrevMainMenuTab <> MainMenuTab Then
			DeleteMenuGadgets()
		EndIf
		If ShouldDeleteGadgets Then
			DeleteMenuGadgets()
		EndIf
		PrevMainMenuTab = MainMenuTab
		ShouldDeleteGadgets = False
		
		If MainMenuTab = MainMenuTab_Default Then
			For i = 0 To 3
				Temp = False
				x = 159 * MenuScale
				y = (286 + 100 * i) * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Temp = (MouseHit1 And MouseOn(x, y, Width, Height))
				
				Local Txt$
				
				Select i
					Case 0
						;[Block]
						Txt = "NEW GAME"
						RandomSeed = ""
						If Temp Then 
							If Rand(15) = 1 Then 
								Select Rand(13)
									Case 1 
										;[Block]
										RandomSeed = "NIL"
										;[End Block]
									Case 2
										;[Block]
										RandomSeed = "NO"
										;[End Block]
									Case 3
										;[Block]
										RandomSeed = "d9341"
										;[End Block]
									Case 4
										;[Block]
										RandomSeed = "5CP_I73"
										;[End Block]
									Case 5
										;[Block]
										RandomSeed = "DONTBLINK"
										;[End Block]
									Case 6
										;[Block]
										RandomSeed = "CRUNCH"
										;[End Block]
									Case 7
										;[Block]
										RandomSeed = "die"
										;[End Block]
									Case 8
										;[Block]
										RandomSeed = "HTAED"
										;[End Block]
									Case 9
										;[Block]
										RandomSeed = "rustledjim"
										;[End Block]
									Case 10
										;[Block]
										RandomSeed = "larry"
										;[End Block]
									Case 11
										;[Block]
										RandomSeed = "JORGE"
										;[End Block]
									Case 12
										;[Block]
										RandomSeed = "dirtymetal"
										;[End Block]
									Case 13
										;[Block]
										RandomSeed = "whatpumpkin"
										;[End Block]
								End Select
							Else
								n = Rand(4, 8)
								For j = 1 To n
									If Rand(3) = 1 Then
										RandomSeed = RandomSeed + Rand(0, 9)
									Else
										RandomSeed = RandomSeed + Chr(Rand(97, 122))
									EndIf
								Next							
							EndIf
							MainMenuTab = MainMenuTab_New_Game
						EndIf
						;[End Block]
					Case 1
						;[Block]
						Txt = "LOAD GAME"
						If Temp Then
							LoadSaveGames()
							MainMenuTab = MainMenuTab_Load_Game
						EndIf
						;[End Block]
					Case 2
						;[Block]
						Txt = "OPTIONS"
						If Temp Then MainMenuTab = MainMenuTab_Options_Graphics
						;[End Block]
					Case 3
						;[Block]
						Txt = "QUIT"
						If Temp Then
							StopChannel(CurrMusicStream)
							End()
						EndIf
						;[End Block]
				End Select
				
				DrawButton(x, y, Width, Height, Txt)
			Next	
		Else
			x = 159 * MenuScale
			y = 286 * MenuScale
			
			Width = 400 * MenuScale
			Height = 70 * MenuScale
			
			If DrawButton(x + Width + 20 * MenuScale, y, 580 * MenuScale - Width - 20 * MenuScale, Height, "BACK", False) Then 
				Select MainMenuTab
					Case MainMenuTab_New_Game
						;[Block]
						PutINIValue(OptionFile, "Global", "Enable Intro", IntroEnabled)
						MainMenuTab = MainMenuTab_Default
						;[End Block]
					Case MainMenuTab_Load_Game
						;[Block]
						CurrLoadGamePage = 0
						MainMenuTab = MainMenuTab_Default
						;[End Block]
					Case MainMenuTab_Options_Graphics, MainMenuTab_Options_Audio, MainMenuTab_Options_Controls, MainMenuTab_Options_Advanced ; ~ Save the options
						;[Block]
						SaveOptionsINI()
						
						UserTrackCheck = 0
						UserTrackCheck2 = 0
						
						CurrLoadGamePage = 0
						AntiAlias(Opt_AntiAlias)
						MainMenuTab = MainMenuTab_Default
						;[End Block]
					Case MainMenuTab_Load_Map ; ~ Move back to the "New Game" tab
						;[Block]
						MainMenuTab = MainMenuTab_New_Game
						CurrLoadGamePage = 0
						MouseHit1 = False
						;[End Block]
					Default
						;[Block]
						MainMenuTab = MainMenuTab_Default
						;[End Block]
				End Select
			EndIf
			
			Select MainMenuTab
				Case MainMenuTab_New_Game
					;[Block]
					x = 159 * MenuScale
					y = 286 * MenuScale
					
					Width = 400 * MenuScale
					Height = 70 * MenuScale
					
					y = y + Height + 20 * MenuScale
					Width = 580 * MenuScale
					Height = 330 * MenuScale
					
					CurrSave = InputBox(x + 150 * MenuScale, y + 15 * MenuScale, 200 * MenuScale, 30 * MenuScale, CurrSave, 1, 15)
					If SelectedInputBox = 1 Then
						CurrSave = Replace(CurrSave, ":", "")
						CurrSave = Replace(CurrSave, ".", "")
						CurrSave = Replace(CurrSave, "/", "")
						CurrSave = Replace(CurrSave, "\", "")
						CurrSave = Replace(CurrSave, "<", "")
						CurrSave = Replace(CurrSave, ">", "")
						CurrSave = Replace(CurrSave, "|", "")
						CurrSave = Replace(CurrSave, "?", "")
						CurrSave = Replace(CurrSave, Chr(34), "")
						CurrSave = Replace(CurrSave, "*", "")
						CursorPos = Min(CursorPos, Len(CurrSave))
					EndIf
					
					If SelectedMap = "" Then
						RandomSeed = InputBox(x + 150 * MenuScale, y + 55 * MenuScale, 200 * MenuScale, 30 * MenuScale, RandomSeed, 3, 15)	
					Else
						If DrawButton(x + 370 * MenuScale, y + 55 * MenuScale, 120 * MenuScale, 30 * MenuScale, "Deselect", False) Then
							ShouldDeleteGadgets = True
							SelectedMap = ""
						EndIf
					EndIf	
					
					IntroEnabled = DrawTick(x + 280 * MenuScale, y + 110 * MenuScale, IntroEnabled)	
					
					For i = SAFE To ESOTERIC
						Local PrevSelectedDifficulty.Difficulty = SelectedDifficulty
						
						If DrawTick(x + 20 * MenuScale, y + (180 + 30 * i) * MenuScale, (SelectedDifficulty = difficulties[i])) Then SelectedDifficulty = difficulties[i]
						
						If PrevSelectedDifficulty <> SelectedDifficulty Then
							If PrevSelectedDifficulty = difficulties[ESOTERIC] Then
								ShouldDeleteGadgets = True
							EndIf
						EndIf
					Next
					
					If SelectedDifficulty\Customizable Then
						SelectedDifficulty\PermaDeath =  DrawTick(x + 160 * MenuScale, y + 165 * MenuScale, (SelectedDifficulty\PermaDeath))
						
						If DrawTick(x + 160 * MenuScale, y + 195 * MenuScale, SelectedDifficulty\SaveType = SAVEANYWHERE And (Not SelectedDifficulty\PermaDeath), SelectedDifficulty\PermaDeath) Then 
							SelectedDifficulty\SaveType = SAVEANYWHERE
						Else
							SelectedDifficulty\SaveType = SAVEONSCREENS
						EndIf
						
						SelectedDifficulty\AggressiveNPCs =  DrawTick(x + 160 * MenuScale, y + 225 * MenuScale, SelectedDifficulty\AggressiveNPCs)
						
						; ~ Other factor's difficulty
						If MouseHit1 Then
							If ImageRectOverlap(ArrowIMG[1], x + 155 * MenuScale, y + 251 * MenuScale, ScaledMouseX(), ScaledMouseY(), 0, 0)
								If SelectedDifficulty\OtherFactors < HARD
									SelectedDifficulty\OtherFactors = SelectedDifficulty\OtherFactors + 1
								Else
									SelectedDifficulty\OtherFactors = EASY
								EndIf
								PlaySound_Strict(ButtonSFX)
							EndIf
						EndIf
					EndIf
					
					If DrawButton(x, y + Height + 10 * MenuScale, 160 * MenuScale, 70 * MenuScale, "Load map", False) Then
						MainMenuTab = MainMenuTab_Load_Map
						LoadSavedMaps()
					EndIf
					
					If DrawButton(x + 420 * MenuScale, y + Height + 10 * MenuScale, 160 * MenuScale, 70 * MenuScale, "START", False) Then
						If CurrSave = "" Then CurrSave = "untitled"
						
						If RandomSeed = "" Then
							RandomSeed = Abs(MilliSecs())
						EndIf
						
						SeedRnd(GenerateSeedNumber(RandomSeed))
						
						Local SameFound% = False
						
						For i = 1 To SaveGameAmount
							If SaveGames(i - 1) = CurrSave Then SameFound = SameFound + 1
						Next
							
						If SameFound > 0 Then CurrSave = CurrSave + " (" + (SameFound + 1) + ")"
						
						InitNewGame()
						MainMenuOpen = False
						FlushKeys()
						FlushMouse()
						
						PutINIValue(OptionFile, "Global", "Enable Intro", IntroEnabled)
					EndIf
					;[End Block]
				Case MainMenuTab_Load_Game
					;[Block]
					y = y + Height + 20 * MenuScale
					Width = 580 * MenuScale
					Height = 430 * MenuScale
					
					x = 159 * MenuScale
					y = 286 * MenuScale
					
					Width = 400 * MenuScale
					Height = 70 * MenuScale
					
					y = y + Height + 20 * MenuScale
					Width = 580 * MenuScale
					Height = 296 * MenuScale
					
					If CurrLoadGamePage < Ceil(Float(SaveGameAmount) / 5.0) - 1 And SaveMSG = "" Then 
						If DrawButton(x + Width - 50 * MenuScale, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, ">") Then
							CurrLoadGamePage = CurrLoadGamePage + 1
							ShouldDeleteGadgets = True
						EndIf
					Else
						DrawButton(x + Width - 50 * MenuScale, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, ">", True, False, True)
					EndIf
					If CurrLoadGamePage > 0 And SaveMSG = "" Then
						If DrawButton(x, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, "<") Then
							CurrLoadGamePage = CurrLoadGamePage - 1
							ShouldDeleteGadgets = True
						EndIf
					Else
						DrawButton(x, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, "<", True, False, True)
					EndIf
					
					If CurrLoadGamePage > Ceil(Float(SaveGameAmount) / 5.0) - 1 Then
						CurrLoadGamePage = CurrLoadGamePage - 1
						ShouldDeleteGadgets = True
					EndIf
					
					If SaveGameAmount <> 0 Then
						x = x + 20 * MenuScale
						y = y + 20 * MenuScale
						
						For i = (1 + (5 * CurrLoadGamePage)) To 5 + (5 * CurrLoadGamePage)
							If i =< SaveGameAmount Then
								If SaveMSG = "" Then
									If SaveGameVersion(i - 1) <> VersionNumber Then
										DrawButton(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False, False, True, 255, 0, 0)
									Else
										If DrawButton(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False) Then
											LoadEntities()
											LoadAllSounds()
											LoadGame(SavePath + SaveGames(i - 1) + "\")
											CurrSave = SaveGames(i - 1)
											InitLoadGame()
											MainMenuOpen = False
											ShouldDeleteGadgets = True
										EndIf
									EndIf
										
									If DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Delete", False) Then
										SaveMSG = SaveGames(i - 1)
										Exit
									EndIf
								Else
									If SaveGameVersion(i - 1) <> VersionNumber Then
										DrawButton(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False, False, True, 255, 0, 0)
									Else
										DrawButton(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False, False, True)
									EndIf
									DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Delete", False, False, True)
								EndIf
								y = y + 80 * MenuScale
							Else
								Exit
							EndIf
						Next
						
						If SaveMSG <> "" Then
							x = 740 * MenuScale
							y = 376 * MenuScale
							
							If DrawButton(x + 50 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Yes", False) Then
								DeleteFile(CurrentDir() + SavePath + SaveMSG + "\save.cb")
								DeleteDir(CurrentDir() + SavePath + SaveMSG)
								SaveMSG = ""
								LoadSaveGames()
								ShouldDeleteGadgets = True
							EndIf
							If DrawButton(x + 250 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, "No", False) Then
								SaveMSG = ""
								ShouldDeleteGadgets = True
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case MainMenuTab_Options_Graphics, MainMenuTab_Options_Audio, MainMenuTab_Options_Controls, MainMenuTab_Options_Advanced
					;[Block]
					x = 159 * MenuScale
					y = 286 * MenuScale
					
					Width = 400 * MenuScale
					Height = 70 * MenuScale
					
					y = y + Height + 20 * MenuScale
					Width = 580 * MenuScale
					Height = 60 * MenuScale
					
					If DrawButton(x + 20 * MenuScale, y + 15 * MenuScale, Width / 5, Height / 2, "GRAPHICS", False) Then MainMenuTab = MainMenuTab_Options_Graphics
					If DrawButton(x + 160 * MenuScale, y + 15 * MenuScale, Width / 5, Height / 2, "AUDIO", False) Then MainMenuTab = MainMenuTab_Options_Audio
					If DrawButton(x + 300 * MenuScale, y + 15 * MenuScale, Width / 5, Height / 2, "CONTROLS", False) Then MainMenuTab = MainMenuTab_Options_Controls
					If DrawButton(x + 440 * MenuScale, y + 15 * MenuScale, Width / 5, Height / 2, "ADVANCED", False) Then MainMenuTab = MainMenuTab_Options_Advanced
					
					y = y + 70 * MenuScale
					
					If MainMenuTab <> MainMenuTab_Options_Audio Then
						UserTrackCheck = 0
						UserTrackCheck2 = 0
					EndIf
					
					If MainMenuTab = MainMenuTab_Options_Graphics
						;[Block]
						Height = 410 * MenuScale
						
						y = y + 20 * MenuScale
						
						BumpEnabled = DrawTick(x + 310 * MenuScale, y + MenuScale, BumpEnabled)
						
						y = y + 30 * MenuScale
						
						VSync = DrawTick(x + 310 * MenuScale, y + MenuScale, VSync)
						
						y = y + 30 * MenuScale
						
						Opt_AntiAlias = DrawTick(x + 310 * MenuScale, y + MenuScale, Opt_AntiAlias)
						
						y = y + 30 * MenuScale
						
						EnableRoomLights = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableRoomLights)
						
						y = y + 40 * MenuScale
						
						ScreenGamma = (SlideBar(x + 310 * MenuScale, y, 150 * MenuScale, ScreenGamma * 50.0) / 50.0)
						
						y = y + 50 * MenuScale
						
						ParticleAmount = Slider3(x + 310  * MenuScale, y, 150 * MenuScale, ParticleAmount, 2, "MINIMAL", "REDUCED", "FULL")
						
						y = y + 50 * MenuScale
						
						TextureDetails = Slider5(x + 310 * MenuScale, y, 150 * MenuScale, TextureDetails, 3, "0.8", "0.4", "0.0", "-0.4", "-0.8")
						Select TextureDetails
							Case 0
								;[Block]
								TextureFloat = 0.8
								;[End Block]
							Case 1
								;[Block]
								TextureFloat = 0.4
								;[End Block]
							Case 2
								;[Block]
								TextureFloat = 0.0
								;[End Block]
							Case 3
								;[Block]
								TextureFloat = -0.4
								;[End Block]
							Case 4
								;[Block]
								TextureFloat = -0.8
								;[End Block]
						End Select
						TextureLodBias(TextureFloat)
						
						y = y + 50 * MenuScale
						
						SaveTexturesInVRAM = DrawTick(x + 310 * MenuScale, y + MenuScale, SaveTexturesInVRAM)
						
						y = y + 50 * MenuScale
						
						CurrFOV = (SlideBar(x + 310 * MenuScale, y, 150 * MenuScale, CurrFOV * 2.0) / 2.0)
						FOV = CurrFOV + 40
						;[End Block]
					ElseIf MainMenuTab = MainMenuTab_Options_Audio
						;[Block]
						Height = 220 * MenuScale
						
						y = y + 20 * MenuScale
						
						MusicVolume = (SlideBar(x + 310 * MenuScale, y, 150 * MenuScale, MusicVolume * 100.0) / 100.0)
						
						y = y + 40 * MenuScale
						
						PrevSFXVolume = (SlideBar(x + 310 * MenuScale, y, 150 * MenuScale, SFXVolume * 100.0) / 100.0)
						SFXVolume = PrevSFXVolume
						
						y = y + 40 * MenuScale
						
						EnableSFXRelease = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableSFXRelease)
						If EnableSFXRelease_Prev <> EnableSFXRelease
							If EnableSFXRelease Then
								For snd.Sound = Each Sound
									For i = 0 To 31
										If snd\Channels[i] <> 0 Then
											If ChannelPlaying(snd\Channels[i]) = True Then
												StopChannel(snd\Channels[i])
											EndIf
										EndIf
									Next
									If snd\InternalHandle <> 0 Then
										FreeSound(snd\InternalHandle)
										snd\InternalHandle = 0
									EndIf
									snd\ReleaseTime = 0
								Next
							Else
								For snd.Sound = Each Sound
									If snd\InternalHandle = 0 Then snd\InternalHandle = LoadSound(snd\Name)
								Next
							EndIf
							EnableSFXRelease_Prev = EnableSFXRelease
						EndIf
						
						y = y + 30 * MenuScale
						
						Local PrevEnableUserTracks% = EnableUserTracks
						
						EnableUserTracks = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableUserTracks)
						
						If PrevEnableUserTracks Then
							If PrevEnableUserTracks <> EnableUserTracks Then
								ShouldDeleteGadgets = True
							EndIf
						EndIf
						
						If EnableUserTracks Then
							y = y + 30 * MenuScale
							
							UserTrackMode = DrawTick(x + 310 * MenuScale, y + MenuScale, UserTrackMode)
							
							If DrawButton(x + 20 * MenuScale, y + 30 * MenuScale, 210 * MenuScale, 25 * MenuScale, "Scan for User Tracks", False)
								UserTrackCheck = 0
								UserTrackCheck2 = 0
								
								Dir = ReadDir("SFX\Radio\UserTracks\")
								Repeat
									File = NextFile(Dir)
									If File = "" Then Exit
									If FileType("SFX\Radio\UserTracks\" + File$) = 1 Then
										UserTrackCheck = UserTrackCheck + 1
										Test = LoadSound("SFX\Radio\UserTracks\" + File$)
										If Test <> 0 Then
											UserTrackCheck2 = UserTrackCheck2 + 1
										EndIf
										FreeSound(Test)
									EndIf
								Forever
								CloseDir(Dir)
							EndIf
						Else
							UserTrackCheck = 0
						EndIf
						;[End Block]
					ElseIf MainMenuTab = MainMenuTab_Options_Controls
						;[Block]
						Height = 300 * MenuScale
						
						y = y + 20 * MenuScale
						
						MouseSensitivity = (SlideBar(x + 310 * MenuScale, y, 150 * MenuScale, (MouseSensitivity + 0.5) * 100.0) / 100.0) - 0.5
						
						y = y + 40 * MenuScale
						
						InvertMouse = DrawTick(x + 310 * MenuScale, y + MenuScale, InvertMouse)
						
						y = y + 40 * MenuScale
						
						MouseSmoothing = (SlideBar(x + 310 * MenuScale, y, 150 * MenuScale, (MouseSmoothing) * 50.0) / 50.0)
						
						y = y + 30 * MenuScale
						
						y = y + 10 * MenuScale
						
						InputBox(x + 160 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_UP, 210.0)], 5)		
						
						InputBox(x + 160 * MenuScale, y + 40 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_LEFT, 210.0)], 3)	
						
						InputBox(x + 160 * MenuScale, y + 60 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_DOWN, 210.0)], 6)				
						
						InputBox(x + 160 * MenuScale, y + 80 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_RIGHT, 210.0)], 4)	
						
						InputBox(x + 160 * MenuScale, y + 100 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\SAVE, 210.0)], 11)
						
						InputBox(x + 470 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\BLINK, 210.0)], 7)				
						
						InputBox(x + 470 * MenuScale, y + 40 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\SPRINT, 210.0)], 8)
						
						InputBox(x + 470 * MenuScale, y + 60 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\INVENTORY, 210.0)], 9)
						
						InputBox(x + 470 * MenuScale, y + 80 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\CROUCH, 210.0)], 10)	
						
						InputBox(x + 470 * MenuScale, y + 100 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\CONSOLE, 210.0)], 12)
						
						InputBox(x + 470 * MenuScale, y + 120 * MenuScale, 100 * MenuScale, 20 * MenuScale, key\Name[Min(key\SCREENSHOT, 210.0)], 13)
						
						Local TempKey%
						
						For i = 0 To 227
							If KeyHit(i) Then TempKey = i : Exit
						Next
						If TempKey <> 0 Then
							Select SelectedInputBox
								Case 3
									;[Block]
									key\MOVEMENT_LEFT = TempKey
									;[End Block]
								Case 4
									;[Block]
									key\MOVEMENT_RIGHT = TempKey
									;[End Block]
								Case 5
									;[Block]
									key\MOVEMENT_UP = TempKey
									;[End Block]
								Case 6
									;[Block]
									key\MOVEMENT_DOWN = TempKey
									;[End Block]
								Case 7
									;[Block]
									key\BLINK = TempKey
									;[End Block]
								Case 8
									;[Block]
									key\SPRINT = TempKey
									;[End Block]
								Case 9
									;[Block]
									key\INVENTORY = TempKey
									;[End Block]
								Case 10
									;[Block]
									key\CROUCH = TempKey
									;[End Block]
								Case 11
									;[Block]
									key\SAVE = TempKey
									;[End Block]
								Case 12
									;[Block]
									key\CONSOLE = TempKey
									;[End Block]
								Case 13
									;[Block]
									key\SCREENSHOT = TempKey
									;[End Block]
							End Select
							SelectedInputBox = 0
						EndIf
						;[End Block]
					ElseIf MainMenuTab = MainMenuTab_Options_Advanced
						;[Block]
						Height = 330 * MenuScale
						
						If CurrLoadGamePage = 0 Then 
							If DrawButton(x + Width - 30 * MenuScale, y + Height + 5 * MenuScale, 30 * MenuScale, 30 * MenuScale, ">", False) Then
								CurrLoadGamePage = CurrLoadGamePage + 1
								ShouldDeleteGadgets = True
							EndIf
						Else
							DrawButton(x + Width - 30 * MenuScale, y + Height + 5 * MenuScale, 30 * MenuScale, 30 * MenuScale, ">", False, False, True)
						EndIf
						If CurrLoadGamePage = 1 Then
							If DrawButton(x, y + Height + 5 * MenuScale, 30 * MenuScale, 30 * MenuScale, "<", False) Then
								CurrLoadGamePage = CurrLoadGamePage - 1
								ShouldDeleteGadgets = True
							EndIf
						Else
							DrawButton(x, y + Height + 5 * MenuScale, 30 * MenuScale, 30 * MenuScale, "<", False, False, True)
						EndIf
						
						If CurrLoadGamePage = 0 Then
							y = y + 20 * MenuScale
							
							HUDEnabled = DrawTick(x + 310 * MenuScale, y + MenuScale, HUDenabled)
							
							y = y + 30 * MenuScale
							
							Local PrevCanOpenConsole% = CanOpenConsole
							
							CanOpenConsole = DrawTick(x + 310 * MenuScale, y + MenuScale, CanOpenConsole)
							
							If PrevCanOpenConsole Then
								If PrevCanOpenConsole <> CanOpenConsole
									ShouldDeleteGadgets = True
								EndIf
							EndIf
							
							y = y + 30 * MenuScale
							
							If CanOpenConsole Then
								ConsoleOpening = DrawTick(x + 310 * MenuScale, y + MenuScale, ConsoleOpening)
							EndIf
							
							y = y + 30 * MenuScale
							
							AchvMsgEnabled = DrawTick(x + 310 * MenuScale, y + MenuScale, AchvMSGenabled)
							
							y = y + 50 * MenuScale
							
							ShowFPS = DrawTick(x + 310 * MenuScale, y + MenuScale, ShowFPS)
							
							y = y + 30 * MenuScale
							
							Local PrevCurrFrameLimit% = CurrFrameLimit > 0.0
							
							If DrawTick(x + 310 * MenuScale, y, CurrFrameLimit > 0.0) Then
								CurrFrameLimit = (SlideBar(x + 150 * MenuScale, y + 30 * MenuScale, 100 * MenuScale, CurrFrameLimit * 99.0) / 99.0)
								CurrFrameLimit = Max(CurrFrameLimit, 0.01)
								FrameLimit = 19 + (CurrFrameLimit * 100.0)
							Else
								CurrFrameLimit = 0.0
								FrameLimit = 0
							EndIf
							
							If PrevCurrFrameLimit Then
								If PrevCurrFrameLimit <> CurrFrameLimit Then
									ShouldDeleteGadgets = True
								EndIf
							EndIf
						Else
							y = y + 20 * MenuScale
							
							If HUDEnabled Then
								BarStyle = DrawTick(x + 310 * MenuScale, y + MenuScale, BarStyle)
							EndIf
							
							y = y + 30 * MenuScale
							
							PlayStartup = DrawTick(x + 310 * MenuScale, y + MenuScale, PlayStartup)
							
							y = y + 30 * MenuScale
							
							LauncherEnabled = DrawTick(x + 310 * MenuScale, y + MenuScale, LauncherEnabled)
							
							y = y + 30 * MenuScale
							
							If DrawButton(x + 20 * MenuScale, y, 220, 30, "RESET OPTIONS", False) Then
								Delay(200)
								ResetOptionsINI()
								SaveOptionsINI(True)
							EndIf
						EndIf
						;[End Block]
					EndIf
					;[End Block]
				Case MainMenuTab_Load_Map
					;[Block]
					y = y + Height + 20 * MenuScale
					Width = 580 * MenuScale
					Height = 430 * MenuScale
					
					x = 159 * MenuScale
					y = 286 * MenuScale
					
					Width = 400 * MenuScale
					Height = 70 * MenuScale
					
					y = y + Height + 20 * MenuScale
					Width = 580 * MenuScale
					Height = 350 * MenuScale
					
					If CurrLoadGamePage < Ceil(Float(SavedMapsAmount) / 5.0) - 1 Then 
						If DrawButton(x + Width - 50 * MenuScale, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, ">") Then
							CurrLoadGamePage = CurrLoadGamePage + 1
							ShouldDeleteGadgets = True
						EndIf
					Else
						DrawButton(x + Width - 50 * MenuScale, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, ">", True, False, True)
					EndIf
					If CurrLoadGamePage > 0 Then
						If DrawButton(x, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, "<") Then
							CurrLoadGamePage = CurrLoadGamePage - 1
							ShouldDeleteGadgets = True
						EndIf
					Else
						DrawButton(x, y + 440 * MenuScale, 50 * MenuScale, 50 * MenuScale, "<", True, False, True)
					EndIf
					
					If CurrLoadGamePage > Ceil(Float(SavedMapsAmount) / 5.0) - 1 Then
						CurrLoadGamePage = CurrLoadGamePage - 1
						ShouldDeleteGadgets = True
					EndIf
					
					If SavedMaps(0) <> "" Then 
						x = x + 20 * MenuScale
						y = y + 20 * MenuScale
						For i = (1 + (5 * CurrLoadGamePage)) To 5 + (5 * CurrLoadGamePage)
							If i =< SavedMapsAmount Then
								If DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False) Then
									SelectedMap = SavedMaps(i - 1)
									MainMenuTab = MainMenuTab_New_Game
									ShouldDeleteGadgets = True
								EndIf
								y = y + 80 * MenuScale
							Else
								Exit
							EndIf
						Next
					EndIf
					;[End Block]
			End Select
		EndIf
	Wend
	
	RenderMainMenu()
	
	Color(255, 255, 255)
	SetFont(fo\ConsoleFont)
	Text(20, GraphicHeight - 50, "v" + VersionNumber)
	If ShowFPS Then SetFont(fo\ConsoleFont) : Text(20, GraphicHeight - 30, "FPS: " + ft\fps) : SetFont(fo\FontID[0])
	
	If DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
	
	SetFont(fo\FontID[0])
End Function

Function RenderMainMenu()
	Local x%, y%, Width%, Height%, Temp%, i%
	
	Color(0, 0, 0)
	
	ShowPointer()
	
	DrawImage(MenuBack, 0, 0)
	
	If (MilliSecs2() Mod MenuBlinkTimer[0]) >= Rand(MenuBlinkDuration[0]) Then
		DrawImage(Menu173, GraphicWidth - ImageWidth(Menu173), GraphicHeight - ImageHeight(Menu173))
	EndIf
	
	SetFont(fo\FontID[0])
	
	If MenuBlinkTimer[1] < MenuBlinkDuration[1] Then
		Color(50, 50, 50)
		Text(MenuStrX + Rand(-5, 5), MenuStrY + Rand(-5, 5), MenuStr, True)
		If MenuBlinkTimer[1] < 0 Then
			MenuStrX = Rand(700, 1000) * MenuScale
			MenuStrY = Rand(100, 600) * MenuScale
			
			Select Rand(0, 23)
				Case 0, 2, 3
					;[Block]
					MenuStr = "DON'T BLINK"
					;[End Block]
				Case 4, 5
					;[Block]
					MenuStr = "Secure. Contain. Protect."
					;[End Block]
				Case 6, 7, 8
					;[Block]
					MenuStr = "You want happy endings? Fuck you."
					;[End Block]
				Case 9, 10, 11
					;[Block]
					MenuStr = "Sometimes we would have had time to scream."
					;[End Block]
				Case 12, 19
					;[Block]
					MenuStr = "NIL"
					;[End Block]
				Case 13
					;[Block]
					MenuStr = "NO"
					;[End Block]
				Case 14
					;[Block]
					MenuStr = "black white black white black white gray"
					;[End Block]
				Case 15
					;[Block]
					MenuStr = "Stone does not care"
					;[End Block]
				Case 16
					;[Block]
					MenuStr = "9341"
					;[End Block]
				Case 17
					;[Block]
					MenuStr = "It controls the doors"
					;[End Block]
				Case 18
					;[Block]
					MenuStr = "e8m106]af173o+079m895w914"
					;[End Block]
				Case 20
					;[Block]
					MenuStr = "It has taken over everything"
					;[End Block]
				Case 21
					;[Block]
					MenuStr = "The spiral is growing"
					;[End Block]
				Case 22
					;[Block]
					MenuStr = Chr(34) + "Some kind of gestalt effect due to massive reality damage." + Chr(34)
					;[End Block]
				Case 23
					;[Block]
					MenuStr = "Does the Black Moon howl? Yes. No. Yes. No."
					;[End Block]
			End Select
		EndIf
	EndIf
	
	SetFont(fo\FontID[1])
	
	DrawImage(MenuText, GraphicWidth / 2 - ImageWidth(MenuText) / 2, GraphicHeight - 20 * MenuScale - ImageHeight(MenuText))
	
	If GraphicWidth > 1240 * MenuScale Then
		DrawTiledImageRect(MenuWhite, 0, 5, 512, 7 * MenuScale, 985.0 * MenuScale, 407.0 * MenuScale, (GraphicWidth - 1240 * MenuScale) + 300, 7 * MenuScale)
	EndIf
	
	If MainMenuTab <> MainMenuTab_Default Then
		x = 159 * MenuScale
		y = 286 * MenuScale
		
		Width = 400 * MenuScale
		Height = 70 * MenuScale
		
		DrawFrame(x, y, Width, Height)
		
		Select MainMenuTab
			Case MainMenuTab_New_Game
				;[Block]
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont(fo\FontID[1])
				Text(x + Width / 2, y + Height / 2, "NEW GAME", True, True)
				
				y = y + Height + 20 * MenuScale
				Width = 580 * MenuScale
				Height = 330 * MenuScale
				
				DrawFrame(x, y, Width, Height)				
				
				SetFont(fo\FontID[0])
				
				Text(x + 20 * MenuScale, y + 20 * MenuScale, "Name:")
				
				Color(255, 255, 255)
				If SelectedMap = "" Then
					Text(x + 20 * MenuScale, y + 60 * MenuScale, "Map seed:")
				Else
					Text(x + 20 * MenuScale, y + 60 * MenuScale, "Selected map:")
					Color(255, 255, 255)
					Rect(x + 150 * MenuScale, y + 55 * MenuScale, 200 * MenuScale, 30 * MenuScale)
					Color(0, 0, 0)
					Rect(x + 150 * MenuScale + 2, y + 55 * MenuScale + 2, 200 * MenuScale - 4, 30 * MenuScale - 4)
					
					Color(255, 0, 0)
					If Len(SelectedMap) > 15 Then
						Text(x + 150 * MenuScale + 100 * MenuScale, y + 55 * MenuScale + 15 * MenuScale, Left(SelectedMap, 14) + "...", True, True)
					Else
						Text(x + 150 * MenuScale + 100 * MenuScale, y + 55 * MenuScale + 15 * MenuScale, SelectedMap, True, True)
					EndIf
				EndIf	
				
				Color(255, 255, 255)
				Text(x + 20 * MenuScale, y + 110 * MenuScale, "Enable intro sequence:")
				
				Text(x + 20 * MenuScale, y + 150 * MenuScale, "Difficulty:")				
				For i = SAFE To ESOTERIC
					Color(difficulties[i]\R, difficulties[i]\G, difficulties[i]\B)
					Text(x + 60 * MenuScale, y + (180 + 30 * i) * MenuScale, difficulties[i]\Name)
				Next
				
				Color(255, 255, 255)
				DrawFrame(x + 150 * MenuScale, y + 155 * MenuScale, 410 * MenuScale, 150 * MenuScale)
				
				If SelectedDifficulty\Customizable Then
					Text(x + 200 * MenuScale, y + 165 * MenuScale, "Permadeath")
					
					Text(x + 200 * MenuScale, y + 195 * MenuScale, "Save anywhere")	
					
					Text(x + 200 * MenuScale, y + 225 * MenuScale, "Aggressive NPCs")
					
					; ~ Other factor's difficulty
					Color(255, 255, 255)
					DrawImage(ArrowIMG[1], x + 155 * MenuScale, y + 251 * MenuScale)
					
					Color(255, 255, 255)
					Select SelectedDifficulty\OtherFactors
						Case EASY
							;[Block]
							Text(x + 200 * MenuScale, y + 255 * MenuScale, "Other difficulty factors: Easy")
							;[End Block]
						Case NORMAL
							;[Block]
							Text(x + 200 * MenuScale, y + 255 * MenuScale, "Other difficulty factors: Normal")
							;[End Block]
						Case HARD
							;[Block]
							Text(x + 200 * MenuScale, y + 255 * MenuScale, "Other difficulty factors: Hard")
							;[End Block]
					End Select
				Else
					RowText(SelectedDifficulty\Description, x + 160 * MenuScale, y + 160 * MenuScale, (410 - 20) * MenuScale, 200)					
				EndIf
				
				SetFont(fo\FontID[1])
				;[End Block]
			Case MainMenuTab_Load_Game
				;[Block]
				y = y + Height + 20 * MenuScale
				Width = 580 * MenuScale
				Height = 430 * MenuScale
				
				DrawFrame(x, y, Width, Height)
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont(fo\FontID[1])
				Text(x + Width / 2, y + Height / 2, "LOAD GAME", True, True)
				
				y = y + Height + 20 * MenuScale
				Width = 580 * MenuScale
				Height = 296 * MenuScale
				
				SetFont(fo\FontID[1])
				
				DrawFrame(x + 60 * MenuScale, y + 440 * MenuScale, Width - 120 * MenuScale, 50 * MenuScale)
				
				Text(x + (Width / 2.0), y + 465 * MenuScale, "Page " + Int(Max((CurrLoadGamePage + 1), 1)) + "/" + Int(Max((Int(Ceil(Float(SaveGameAmount) / 5.0))), 1)), True, True)
				
				SetFont(fo\FontID[0])
				
				If SaveGameAmount = 0 Then
					Text(x + 20 * MenuScale, y + 20 * MenuScale, "No saved games.")
				Else
					x = x + 20 * MenuScale
					y = y + 20 * MenuScale
					
					For i = (1 + (5 * CurrLoadGamePage)) To 5 + (5 * CurrLoadGamePage)
						If i =< SaveGameAmount Then
							DrawFrame(x, y, 540 * MenuScale, 70 * MenuScale)
							
							If SaveGameVersion(i - 1) <> VersionNumber Then
								Color(255, 0, 0)
							Else
								Color(255, 255, 255)
							EndIf
							
							Text(x + 20 * MenuScale, y + 10 * MenuScale, SaveGames(i - 1))
							Text(x + 20 * MenuScale, y + (10 + 18) * MenuScale, SaveGameTime(i - 1))
							Text(x + 120 * MenuScale, y + (10 + 18) * MenuScale, SaveGameDate(i - 1))
							Text(x + 20 * MenuScale, y + (10 + 36) * MenuScale, SaveGameVersion(i - 1))
							
							y = y + 80 * MenuScale
						Else
							Exit
						EndIf
					Next
					
					If SaveMSG <> "" Then
						x = 740 * MenuScale
						y = 376 * MenuScale
						DrawFrame(x, y, 420 * MenuScale, 200 * MenuScale)
						RowText("Are you sure you want to delete this save?", x + 20 * MenuScale, y + 15 * MenuScale, 400 * MenuScale, 200 * MenuScale)
					EndIf
				EndIf
				;[End Block]
			Case MainMenuTab_Options_Graphics, MainMenuTab_Options_Audio, MainMenuTab_Options_Controls, MainMenuTab_Options_Advanced
				;[Block]
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont(fo\FontID[1])
				Text(x + Width / 2, y + Height / 2, "OPTIONS", True, True)
				
				y = y + Height + 20 * MenuScale
				Width = 580 * MenuScale
				Height = 60 * MenuScale
				DrawFrame(x, y, Width, Height)
				
				Color(0, 255, 0)
				If MainMenuTab = MainMenuTab_Options_Graphics
					Rect(x + 15 * MenuScale, y + 10 * MenuScale, (Width / 5) + 10 * MenuScale, (Height / 2) + 10 * MenuScale, True)
				ElseIf MainMenuTab = MainMenuTab_Options_Audio
					Rect(x + 155 * MenuScale, y + 10 * MenuScale, (Width / 5) + 10 * MenuScale, (Height / 2) + 10 * MenuScale, True)
				ElseIf MainMenuTab = MainMenuTab_Options_Controls
					Rect(x + 295 * MenuScale, y + 10 * MenuScale, (Width / 5) + 10 * MenuScale, (Height / 2) + 10 * MenuScale, True)
				ElseIf MainMenuTab = MainMenuTab_Options_Advanced
					Rect(x + 435 * MenuScale, y + 10 * MenuScale, (Width / 5) + 10 * MenuScale, (Height / 2) + 10 * MenuScale, True)
				EndIf
				
				Color(255, 255, 255)
				
				SetFont(fo\FontID[0])
				y = y + 70 * MenuScale
				
				Local tX# = x + Width
				Local tY# = y
				Local tW# = 400.0 * MenuScale
				Local tH# = 150.0 * MenuScale
				
				If MainMenuTab = MainMenuTab_Options_Graphics
					;[Block]
					Height = 410 * MenuScale
					DrawFrame(x, y, Width, Height)
					
					y = y + 20 * MenuScale
					
					Color(255, 255, 255)				
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Enable bump mapping:")	
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale) And OnSliderID = 0
						DrawOptionsTooltip(tX, tY, tW, tH, "bump")
					EndIf
					
					y = y + 30 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "VSync:")
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale) And OnSliderID = 0
						DrawOptionsTooltip(tX, tY, tW, tH, "vsync")
					EndIf
					
					y = y + 30 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Anti-aliasing:")
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale) And OnSliderID = 0
						DrawOptionsTooltip(tX, tY, tW, tH, "antialias")
					EndIf
					
					y = y + 30 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Enable room lights:")
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale) And OnSliderID = 0
						DrawOptionsTooltip(tX, tY, tW, tH, "roomlights")
					EndIf
					
					y = y + 40 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Screen gamma:")
					If MouseOn(x + 310 * MenuScale, y + 6 * MenuScale, 150 * MenuScale + 14, 20) And OnSliderID = 0
						DrawOptionsTooltip(tX, tY, tW, tH, "gamma", ScreenGamma)
					EndIf
					
					y = y + 50 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Particle amount:")
					If (MouseOn(x + 310 * MenuScale, y - 6 * MenuScale, 150 * MenuScale + 14, 20) And OnSliderID = 0) Lor OnSliderID = 2
						DrawOptionsTooltip(tX, tY, tW, tH, "particleamount", ParticleAmount)
					EndIf
					
					y = y + 50 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Texture LOD Bias:")
					If (MouseOn(x + 310 * MenuScale, y - 6 * MenuScale, 150 * MenuScale + 14, 20) And OnSliderID = 0) Lor OnSliderID = 3
						DrawOptionsTooltip(tX, tY, tW, tH + 100 * MenuScale, "texquality")
					EndIf
					
					y = y + 50 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Save textures in the VRAM:")
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale) And OnSliderID = 0
						DrawOptionsTooltip(tX, tY, tW, tH, "vram")
					EndIf
					
					y = y + 50 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Field of view:")
					Color(255, 255, 0)
					Text(x + 25 * MenuScale, y + 30 * MenuScale, Int(FOV) + "�")
					If MouseOn(x + 310 * MenuScale, y + 6 * MenuScale, 150 * MenuScale + 14, 20)
						DrawOptionsTooltip(tX, tY, tW, tH, "fov")
					EndIf
					;[End Block]
				ElseIf MainMenuTab = MainMenuTab_Options_Audio
					;[Block]
					If EnableUserTracks Then
						Height = 230 * MenuScale
					Else
						Height = 170 * MenuScale
					EndIf
					DrawFrame(x, y, Width, Height)	
					
					y = y + 20 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Music volume:")
					If MouseOn(x + 310 * MenuScale, y - 4 * MenuScale, 150 * MenuScale + 14, 20)
						DrawOptionsTooltip(tX, tY, tW, tH, "musicvol", MusicVolume)
					EndIf
					
					y = y + 40 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Sound volume:")
					If MouseOn(x + 310 * MenuScale, y - 4 * MenuScale, 150 * MenuScale + 14, 20)
						DrawOptionsTooltip(tX, tY, tW, tH, "soundvol", PrevSFXVolume)
					EndIf
					
					y = y + 40 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Sound auto-release:")
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
						DrawOptionsTooltip(tX, tY, tW, tH + 220 * MenuScale, "sfxautorelease")
					EndIf
					y = y + 30 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Enable user tracks:")
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
						DrawOptionsTooltip(tX, tY, tW, tH, "usertrack")
					EndIf
					
					y = y + 30 * MenuScale
					
					If EnableUserTracks Then
						Color(255, 255, 255)
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "User track mode:")
						If UserTrackMode
							Text(x + 350 * MenuScale, y + 4 * MenuScale, "Repeat")
						Else
							Text(x + 350 * MenuScale, y + 4 * MenuScale, "Random")
						EndIf
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "usertrackmode")
						EndIf
						If MouseOn(x + 20 * MenuScale, y + 30 * MenuScale, 190 * MenuScale, 25 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "usertrackscan")
						EndIf
						If UserTrackCheck > 0 Then
							Text(x + 20 * MenuScale, y + 100 * MenuScale, "User tracks found (" + UserTrackCheck2 + "/" + UserTrackCheck + " successfully loaded)")
						EndIf
					EndIf
					;[End Block]
				ElseIf MainMenuTab = MainMenuTab_Options_Controls
					;[Block]
					Height = 300 * MenuScale
					DrawFrame(x, y, Width, Height)	
					
					y = y + 20 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Mouse sensitivity:")
					If MouseOn(x + 310 * MenuScale, y - 4 * MenuScale, 150 * MenuScale + 14, 20)
						DrawOptionsTooltip(tX, tY, tW, tH, "mousesensitivity", MouseSensitivity)
					EndIf
					
					y = y + 40 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Invert mouse Y-axis:")
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
						DrawOptionsTooltip(tX, tY, tW, tH, "mouseinvert")
					EndIf
					
					y = y + 40 * MenuScale
					
					Color(255, 255, 255)
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Mouse smoothing:")
					If MouseOn(x + 310 * MenuScale, y - 4 * MenuScale, 150 * MenuScale + 14, 20)
						DrawOptionsTooltip(tX, tY, tW, tH, "mousesmoothing", MouseSmoothing)
					EndIf
					
					Color(255, 255, 255)
					
					y = y + 30 * MenuScale
					
					Text(x + 20 * MenuScale, y + 4 * MenuScale, "Control configuration:")
					
					y = y + 10 * MenuScale
					
					Text(x + 20 * MenuScale, y + 24 * MenuScale, "Move Forward")
					
					Text(x + 20 * MenuScale, y + 44 * MenuScale, "Strafe Left")
					
					Text(x + 20 * MenuScale, y + 64 * MenuScale, "Move Backward")
					
					Text(x + 20 * MenuScale, y + 84 * MenuScale, "Strafe Right")
					
					Text(x + 20 * MenuScale, y + 104 * MenuScale, "Quick Save")
					
					Text(x + 280 * MenuScale, y + 24 * MenuScale, "Manual Blink")
					
					Text(x + 280 * MenuScale, y + 44 * MenuScale, "Sprint")
					
					Text(x + 280 * MenuScale, y + 64 * MenuScale, "Open/Close Inventory")
					
					Text(x + 280 * MenuScale, y + 84 * MenuScale, "Crouch")
					
					Text(x + 280 * MenuScale, y + 104 * MenuScale, "Open/Close Console")
					
					Text(x + 280 * MenuScale, y + 124 * MenuScale, "Take Screenshot")
					
					If MouseOn(x + 20 * MenuScale, y, Width - 40 * MenuScale, 120 * MenuScale)
						DrawOptionsTooltip(tX, tY, tW, tH, "controls")
					EndIf
					;[End Block]
				ElseIf MainMenuTab = MainMenuTab_Options_Advanced
					;[Block]
					Height = 330 * MenuScale
					
					DrawFrame(x, y, Width, Height)	
					
					DrawFrame(x + 35 * MenuScale, y + Height + 5 * MenuScale, Width - 70 * MenuScale, 30 * MenuScale)	
					
					Text(x + (Width / 2), y + Height + 20 * MenuScale, "Page " + Int(Max((CurrLoadGamePage + 1), 1)) + "/2", True, True)
					
					If CurrLoadGamePage = 0 Then
						y = y + 20 * MenuScale
						
						Color(255, 255, 255)				
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "Show HUD:")	
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "hud")
						EndIf
						
						y = y + 30 * MenuScale
						
						Color(255, 255, 255)
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "Enable console:")
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "consoleenable")
						EndIf
						
						y = y + 30 * MenuScale
						
						If CanOpenConsole Then
							Color(255, 255, 255)
							Text(x + 20 * MenuScale, y + 4 * MenuScale, "Open console on error:")
							If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
								DrawOptionsTooltip(tX, tY, tW, tH, "consoleerror")
							EndIf
						EndIf
						
						y = y + 30 * MenuScale
						
						Color(255, 255, 255)
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "Achievement popups:")
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "achpopup")
						EndIf
						
						y = y + 50 * MenuScale
						
						Color(255, 255, 255)
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "Show FPS:")
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "showfps")
						EndIf
						
						y = y + 30 * MenuScale
						
						Color(255, 255, 255)
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "Framelimit:")
						Color(255, 255, 255)
						If CurrFrameLimit > 0.0 Then
							Color(255, 255, 0)
							Text(x + 25 * MenuScale, y + 34 * MenuScale, FrameLimit + " FPS")
						EndIf
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "framelimit", FrameLimit)
						EndIf
						If MouseOn(x + 150 * MenuScale, y + 30 * MenuScale, 100 * MenuScale + 14, 20)
							DrawOptionsTooltip(tX, tY, tW, tH, "framelimit", FrameLimit)
						EndIf
					Else
						y = y + 20 * MenuScale
						
						If HUDenabled Then
							Color(255, 255, 255)
							Text(x + 20 * MenuScale, y + 4 * MenuScale, "Bar style:")
							If BarStyle = 1 Then
								Text(x + 350 * MenuScale, y + 4 * MenuScale, "Dymanic")
							Else
								Text(x + 350 * MenuScale, y + 4 * MenuScale, "Classic")
							EndIf 
							If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
								DrawOptionsTooltip(tX, tY, tW, tH, "barstyle")
							EndIf
						EndIf
						
						y = y + 30 * MenuScale
						
						Color(255, 255, 255)
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "Play startup videos:")
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "playstartup")
						EndIf
						
						y = y + 30 * MenuScale
						
						Color(255, 255, 255)
						Text(x + 20 * MenuScale, y + 4 * MenuScale, "Use launcher:")
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20 * MenuScale, 20 * MenuScale)
							DrawOptionsTooltip(tX, tY, tW, tH, "uselauncher")
						EndIf
					EndIf
					;[End Block]
				EndIf
				;[End Block]
			Case MainMenuTab_Load_Map
				;[Block]
				y = y + Height + 20 * MenuScale
				Width = 580 * MenuScale
				Height = 430 * MenuScale
				
				DrawFrame(x, y, Width, Height)
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont(fo\FontID[1])
				Text(x + Width / 2, y + Height / 2, "LOAD MAP", True, True)
				
				y = y + Height + 20 * MenuScale
				Width = 580 * MenuScale
				Height = 350 * MenuScale
				
				SetFont(fo\FontID[1])
				
				tX = x + Width
				tY = y
				tW = 400 * MenuScale
				tH = 150 * MenuScale
				
				DrawFrame(x + 60 * MenuScale, y + 440 * MenuScale, Width - 120 * MenuScale, 50 * MenuScale)
				
				Text(x + (Width / 2.0), y + 465 * MenuScale, "Page " + Int(Max((CurrLoadGamePage + 1), 1)) + "/" + Int(Max((Int(Ceil(Float(SavedMapsAmount) / 5.0))), 1)), True, True)
				
				SetFont(fo\FontID[0])
				
				If SavedMaps(0) = "" Then 
					Text(x + 20 * MenuScale, y + 20 * MenuScale, "No saved maps. Use the Map Creator to create new maps.")
				Else
					x = x + 20 * MenuScale
					y = y + 20 * MenuScale
					For i = (1 + (5 * CurrLoadGamePage)) To 5 + (5 * CurrLoadGamePage)
						If i =< SavedMapsAmount Then
							DrawFrame(x, y, 540 * MenuScale, 70 * MenuScale)
							
							Text(x + 20 * MenuScale, y + 10 * MenuScale, SavedMaps(i - 1))
							Text(x + 20 * MenuScale, y + (10 + 27) * MenuScale, SavedMapsAuthor(i - 1))
							
							If MouseOn(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
								DrawMapCreatorTooltip(tX, tY, tW, tH, SavedMaps(i - 1))
							EndIf
							
							y = y + 80 * MenuScale
						Else
							Exit
						EndIf
					Next
				EndIf
				;[End Block]
		End Select
	EndIf
	
	RenderMenuButtons()
	RenderMenuTicks()
	RenderMenuInputBoxes()
	RenderMenuSlideBars()
	RenderMenuSliders()
End Function

Const LauncherWidth% = 640
Const LauncherHeight% = 480

Function UpdateLauncher(lnchr.Launcher)
	Local i%, n%
	
	MenuScale = 1
	
	Graphics3DExt(LauncherWidth, LauncherHeight)
	
	SetBuffer(BackBuffer())
	
	RealGraphicWidth = GraphicWidth
	RealGraphicHeight = GraphicHeight
	
	fo\FontID[0] = LoadFont_Strict("GFX\font\cour\Courier New.ttf", 16, 1)
	SetFont(fo\FontID[0])
	MenuWhite = LoadImage_Strict("GFX\menu\menu_white.png")
	MenuBlack = LoadImage_Strict("GFX\menu\menu_black.png")	
	MaskImage(MenuBlack, 255, 255, 0)
	
	Local LauncherIMG% = LoadImage_Strict("GFX\menu\launcher.png")
	Local LauncherArrowIMG% = LoadImage_Strict("GFX\menu\arrow.png")
	
	RotateImage(LauncherArrowIMG, -90.0)
	MidHandle(LauncherArrowIMG)
	
	For i = 1 To lnchr\TotalGFXModes
		Local SameFound% = False
		
		For n = 0 To lnchr\TotalGFXModes - 1
			If lnchr\GFXModeWidths[n] = GfxModeWidth(i) And lnchr\GFXModeHeights[n] = GfxModeHeight(i) Then SameFound = True : Exit
		Next
		If (Not SameFound) Then
			If GraphicWidth = GfxModeWidth(i) And GraphicHeight = GfxModeHeight(i) Then lnchr\SelectedGFXMode = lnchr\GFXModes
			lnchr\GFXModeWidths[lnchr\GFXModes] = GfxModeWidth(i)
			lnchr\GFXModeHeights[lnchr\GFXModes] = GfxModeHeight(i)
			lnchr\GFXModes = lnchr\GFXModes + 1 
		EndIf
	Next
	
	AppTitle("SCP - Containment Breach Ultimate Edition Launcher")
	
	Local Quit% = False
	
	Repeat
		Cls()
		
		Color(0, 0, 0)
		Rect(0, 0, LauncherWidth, LauncherHeight, True)
		
		MouseHit1 = MouseHit(1)
		
		Color(255, 255, 255)
		DrawImage(LauncherIMG, 0, 0)
		
		Text(20, 240 - 65, "Resolution: ")
		
		Local x% = 40
		Local y% = 205
		
		For i = 0 To lnchr\GFXModes - 1
			Color(0, 0, 0)
			If lnchr\SelectedGFXMode = i Then Rect(x - 1, y - 5, 100, 20, False)
			
			Text(x, y, (lnchr\GFXModeWidths[i] + "x" + lnchr\GFXModeHeights[i]))
			If MouseOn(x - 1, y - 5, 100, 20) Then
				Color(100, 100, 100)
				Rect(x - 1, y - 5, 100, 20, False)
				If MouseHit1 Then lnchr\SelectedGFXMode = i
			EndIf
			
			y = y + 20
			If y >= 250 - 65 + (LauncherHeight - 340) Then y = 270 - 65 : x = x + 100
		Next
		
		LauncherEnabled = DrawLauncherTick(455, 202, LauncherEnabled)
		Text(485, 206, "Use launcher")
		
		Text(455, 234, "Display Mode:")
		
		Local Txt$
		
		Select DisplayMode
			Case 0
				;[Block]
				Txt = "Fullscreen"
				;[End Block]
			Case 1
				;[Block]
				Txt = "Borderless"
				If lnchr\GFXModeWidths[lnchr\SelectedGFXMode] < DesktopWidth() Then
					Text(365, 412, "(upscaled to: " + DesktopWidth() + "x" + DesktopHeight() + ",32)")
				ElseIf lnchr\GFXModeWidths[lnchr\SelectedGFXMode] > DesktopWidth() Then
					Text(365, 412, "(downscaled to: " + DesktopWidth() + "x" + DesktopHeight() + ",32)")
				EndIf
				;[End Block]
			Case 2
				;[Block]
				Txt = "Windowed"
				;[End Block]
		End Select
		Text(478, 262 - 55 + 140, "Current Resolution: " + lnchr\GFXModeWidths[lnchr\SelectedGFXMode] + "x" + lnchr\GFXModeHeights[lnchr\SelectedGFXMode] + ",32", True)
		DrawFrame(455, 254, 120, 30)
		Text(515, 264, Txt, True)
		If DrawLauncherButton(575, 254, 30, 30, "", False) Then
			DisplayMode = (DisplayMode + 1) Mod 3
		EndIf
		DrawImage(LauncherArrowIMG, 589, 268)
		
		If DrawLauncherButton(LauncherWidth - 275, LauncherHeight - 105, 150, 30, "REPORT A BUG!", False, False) Then
		    ExecFile("https://www.moddb.com/mods/scp-containment-breach-ultimate-edition/news/bug-reports1")
			Quit = True
			Exit
		EndIf
		
		If DrawLauncherButton(LauncherWidth - 275, LauncherHeight - 50, 150, 30, "SEE CHANGELOG", False, False) Then
		    ExecFile("Changelog_Reborn.txt")
		EndIf
		
		If DrawLauncherButton(LauncherWidth - 30 - 90, LauncherHeight - 105, 100, 30, "LAUNCH", False, False) Then
			GraphicWidth = lnchr\GFXModeWidths[lnchr\SelectedGFXMode]
			GraphicHeight = lnchr\GFXModeHeights[lnchr\SelectedGFXMode]
			RealGraphicWidth = GraphicWidth
			RealGraphicHeight = GraphicHeight
			Exit
		EndIf
		
		If DrawLauncherButton(LauncherWidth - 30 - 90, LauncherHeight - 50, 100, 30, "EXIT", False, False) Then Quit = True : Exit
		Flip()
	Forever
	
	PutINIValue(OptionFile, "Global", "Width", lnchr\GFXModeWidths[lnchr\SelectedGFXMode])
	PutINIValue(OptionFile, "Global", "Height", lnchr\GFXModeHeights[lnchr\SelectedGFXMode])
	PutINIValue(OptionFile, "Advanced", "Launcher Enabled", LauncherEnabled)
	PutINIValue(OptionFile, "Global", "Display Mode", DisplayMode)
	
	If Quit Then End()
	
	FreeImage(LauncherArrowIMG)
	FreeImage(LauncherIMG)
End Function

Function DrawTiledImageRect(Img%, SrcX%, SrcY%, SrcWidth#, SrcHeight#, x%, y%, Width%, Height%)
	Local x2% = x
	
	While x2 < x + Width
		Local y2% = y
		
		While y2 < y + Height
			If x2 + SrcWidth > x + Width Then SrcWidth = SrcWidth - Max((x2 + SrcWidth) - (x + Width), 1)
			If y2 + SrcHeight > y + Height Then SrcHeight = SrcHeight - Max((y2 + SrcHeight) - (y + Height), 1)
			DrawImageRect(Img, x2, y2, SrcX, SrcY, SrcWidth, SrcHeight)
			y2 = y2 + SrcHeight
		Wend
		x2 = x2 + SrcWidth
	Wend
End Function

Type LoadingScreens
	Field ImgPath$
	Field Img%
	Field ID%
	Field Title$
	Field AlignX%, AlignY%
	Field DisableBackground%
	Field Txt$[5], TxtAmount%
End Type

Function InitLoadingScreens(File$)
	Local TemporaryString$, i%
	Local ls.LoadingScreens
	Local f% = OpenFile(File)
	
	While (Not Eof(f))
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString, 1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			
			ls.LoadingScreens = New LoadingScreens
			LoadingScreenAmount = LoadingScreenAmount + 1
			ls\ID = LoadingScreenAmount
			
			ls\Title = TemporaryString
			ls\ImgPath = GetINIString(File, TemporaryString, "ImgPath")
			
			For i = 0 To 4
				ls\Txt[i] = GetINIString(File, TemporaryString, "Desc" + (i + 1))
				If ls\Txt[i] <> "" Then ls\TxtAmount = ls\TxtAmount + 1
			Next
			
			ls\DisableBackground = GetINIInt(File, TemporaryString, "DisableBackground")
			
			Select Lower(GetINIString(File, TemporaryString, "Align X"))
				Case "left"
					;[Block]
					ls\AlignX = -1
					;[End Block]
				Case "middle", "center"
					;[Block]
					ls\AlignX = 0
					;[End Block]
				Case "right" 
					;[Block]
					ls\AlignX = 1
					;[End Block]
			End Select 
			
			Select Lower(GetINIString(File, TemporaryString, "Align Y"))
				Case "top", "up"
					;[Block]
					ls\AlignY = -1
					;[End Block]
				Case "middle", "center"
					;[Block]
					ls\AlignY = 0
					;[End Block]
				Case "bottom", "down"
					;[Block]
					ls\AlignY = 1
					;[End Block]
			End Select 			
		EndIf
	Wend
	
	CloseFile(f)
End Function

Function DrawLoading(Percent%, ShortLoading% = False)
	Local x%, y%, Temp%, FirstLoop%
	Local ls.LoadingScreens
	
	If Percent = 0 Then
		LoadingScreenText = 0
		
		Temp = Rand(1, LoadingScreenAmount)
		For ls.LoadingScreens = Each LoadingScreens
			If ls\ID = Temp Then
				If ls\Img = 0 Then 
					ls\Img = LoadImage_Strict("LoadingScreens\" + ls\ImgPath + ".png")
					ls\Img = ResizeImage2(ls\Img, ImageWidth(ls\Img) * MenuScale, ImageHeight(ls\Img) * MenuScale)
					SelectedLoadingScreen = ls
				EndIf
				Exit
			EndIf
		Next
	EndIf	
	
	FirstLoop = True
	
	Repeat 
		ClsColor(0, 0, 0)
		Cls()
		
		If Percent > 20 Then
			UpdateMusic()
		EndIf
		
		If (Not ShortLoading) Then
			If Percent > (100.0 / SelectedLoadingScreen\TxtAmount) * (LoadingScreenText + 1) Then
				LoadingScreenText = LoadingScreenText + 1
			EndIf
		EndIf
		
		If (Not SelectedLoadingScreen\DisableBackground) Then
			DrawImage(LoadingBack, GraphicWidth / 2 - ImageWidth(LoadingBack) / 2, GraphicHeight / 2 - ImageHeight(LoadingBack) / 2)
		EndIf	
		
		If SelectedLoadingScreen\AlignX = 0 Then
			x = GraphicWidth / 2 - ImageWidth(SelectedLoadingScreen\Img) / 2 
		ElseIf  SelectedLoadingScreen\AlignX = 1
			x = GraphicWidth - ImageWidth(SelectedLoadingScreen\Img)
		Else
			x = 0
		EndIf
		
		If SelectedLoadingScreen\AlignY = 0 Then
			y = GraphicHeight / 2 - ImageHeight(SelectedLoadingScreen\Img) / 2 
		ElseIf  SelectedLoadingScreen\AlignY = 1
			y = GraphicHeight - ImageHeight(SelectedLoadingScreen\Img)
		Else
			y = 0
		EndIf	
		
		DrawImage(SelectedLoadingScreen\Img, x, y)
		
		Local Width% = 300, Height% = 20, i%
		
		x = GraphicWidth / 2 - Width / 2
		y = GraphicHeight / 2 + 30 - 100
		
		Rect(x, y, Width + 4, Height, False)
		If BarStyle = 1 Then
		    Color(100, 100, 100)
		    Rect(x + 3, y + 3, Float(Percent * 2.98), 14)
		Else
			For i = 1 To Int((Width - 2) * (Percent / 100.0) / 10)
				DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
			Next
		EndIf
		
		Color(255, 255, 255)
		
		If SelectedLoadingScreen\Title = "CWM" Then
			If (Not ShortLoading) Then 
				If FirstLoop Then 
					If Percent = 0 Then
						PlaySound_Strict(LoadTempSound("SFX\SCP\990\cwm1.cwm"))
					ElseIf Percent = 100
						PlaySound_Strict(LoadTempSound("SFX\SCP\990\cwm2.cwm"))
					EndIf
				EndIf
			EndIf
			
			SetFont(fo\FontID[1])
			
			Local Strtemp$ = ""
			
			Temp = Rand(2, 9)
			For i = 0 To Temp
				Strtemp = Strtemp + Chr(Rand(48, 122))
			Next
			Text(GraphicWidth / 2, GraphicHeight / 2 + 80, Strtemp, True, True)
			
			If Percent = 0 Then 
				If Rand(5) = 1 Then
					Select Rand(2)
						Case 1
							;[Block]
							SelectedLoadingScreen\Txt[0] = "It will happen on " + CurrentDate() + "."
							;[End Block]
						Case 2
							;[Block]
							SelectedLoadingScreen\Txt[0] = CurrentTime()
							;[End Block]
					End Select
				Else
					Select Rand(13)
						Case 1
							;[Block]
							SelectedLoadingScreen\Txt[0] = "A very fine radio might prove to be useful."
							;[End Block]
						Case 2
							;[Block]
							SelectedLoadingScreen\Txt[0] = "ThIS PLaCE WiLL BUrN"
							;[End Block]
						Case 3
							;[Block]
							SelectedLoadingScreen\Txt[0] = "You cannot control it."
							;[End Block]
						Case 4
							;[Block]
							SelectedLoadingScreen\Txt[0] = "eof9nsd3jue4iwe1fgj"
						Case 5
							;[Block]
							SelectedLoadingScreen\Txt[0] = "YOU NEED TO TRUST IT"
							;[End Block]
						Case 6 
							;[Block]
							SelectedLoadingScreen\Txt[0] = "Look my friend in the eye when you address him, isn't that the way of the gentleman?"
							;[End Block]
						Case 7
							;[Block]
							SelectedLoadingScreen\Txt[0] = "???____??_???__????n?"
							;[End Block]
						Case 8, 9
							;[Block]
							SelectedLoadingScreen\Txt[0] = "Jorge has been expecting you."
							;[End Block]
						Case 10
							;[Block]
							SelectedLoadingScreen\Txt[0] = "???????????"
							;[End Block]
						Case 11
							;[Block]
							SelectedLoadingScreen\Txt[0] = "Make her a member of the midnight crew."
							;[End Block]
						Case 12
							;[Block]
							SelectedLoadingScreen\Txt[0] = "Concluded that coming here was a mistake. We have to turn back."
							;[End Block]
						Case 13
							;[Block]
							SelectedLoadingScreen\Txt[0] = "This alloy contains the essence of my life."
							;[End Block]
					End Select
				EndIf
			EndIf
			
			Strtemp = SelectedLoadingScreen\Txt[0]
			Temp = Int(Len(SelectedLoadingScreen\Txt[0]) - Rand(5))
			For i = 0 To Rand(10, 15)
				Strtemp = Replace(SelectedLoadingScreen\Txt[0], Mid(SelectedLoadingScreen\Txt[0], Rand(1, Len(Strtemp) - 1), 1), Chr(Rand(130, 250)))
			Next		
			SetFont(fo\FontID[0])
			RowText(Strtemp, GraphicWidth / 2 - 200, GraphicHeight / 2 + 120, 400, 300, True)		
		Else
			
			Color(0, 0, 0)
			SetFont(fo\FontID[1])
			Text(GraphicWidth / 2 + 1, GraphicHeight / 2 + 80 + 1, SelectedLoadingScreen\Title, True, True)
			SetFont(fo\FontID[0])
			RowText(SelectedLoadingScreen\Txt[LoadingScreenText], GraphicWidth / 2 - 200 + 1, GraphicHeight / 2 + 120 + 1, 400, 300, True)
			
			Color(255, 255, 255)
			SetFont(fo\FontID[1])
			Text(GraphicWidth / 2, GraphicHeight / 2 + 80, SelectedLoadingScreen\Title, True, True)
			SetFont(fo\FontID[0])
			RowText(SelectedLoadingScreen\Txt[LoadingScreenText], GraphicWidth / 2 - 200, GraphicHeight / 2 + 120, 400, 300, True)
		EndIf
		
		Color(0, 0, 0)
		Text(GraphicWidth / 2 + 1, GraphicHeight / 2 - 100 + 1, "LOADING - " + Percent + " %", True, True)
		Color(255, 255, 255)
		Text(GraphicWidth / 2, GraphicHeight / 2 - 100, "LOADING - " + Percent + " %", True, True)
		
		If Percent = 100 Then 
			If FirstLoop And SelectedLoadingScreen\Title <> "CWM" Then PlaySound_Strict(LoadTempSound(("SFX\Horror\Horror8.ogg")))
			Text(GraphicWidth / 2, GraphicHeight - 50, "PRESS ANY KEY TO CONTINUE", True, True)
		Else
			FlushKeys()
			FlushMouse()
		EndIf
		
		GammaUpdate()
		
		Flip(False)
		
		FirstLoop = False
		If Percent <> 100 Then Exit
		
		Local Close% = False
		
		If GetKey() <> 0 Lor MouseHit(1)
			FlushKeys()
			FlushMouse()
			ResetTimingAccumulator()
			Close = True
		EndIf
	Until Close
	
	DeleteMenuGadgets()
End Function

Function DrawFrame(x%, y%, Width%, Height%, xOffset% = 0, yOffset% = 0)
	Color(255, 255, 255)
	DrawTiledImageRect(MenuWhite, xOffset, (y Mod 256), 512, 512, x, y, Width, Height)
	DrawTiledImageRect(MenuBlack, yOffset, (y Mod 256), 512, 512, x + 3 * MenuScale, y + 3 * MenuScale, Width - 6 * MenuScale, Height - 6 * MenuScale)	
End Function

Type MenuButton
	Field x%, y%, Width%, Height%
	Field Txt$
	Field BigFont%
	Field Locked%
	Field R%, G%, B%
End Type

Function RenderMenuButtons()
	Local mb.MenuButton
	
	For mb.MenuButton = Each MenuButton
		DrawFrame(mb\x, mb\y, mb\Width, mb\Height)
		If MouseOn(mb\x, mb\y, mb\Width, mb\Height) Then
			Color(30, 30, 30)
			Rect(mb\x + 4, mb\y + 4, mb\Width - 8, mb\Height - 8)	
		Else
			Color(0, 0, 0)
		EndIf
		
		If mb\Locked Then
			If mb\R <> 255 Lor mb\G <> 255 Lor mb\B <> 255 Then
				Color(mb\R, mb\G, mb\B)
			Else
				Color(100, 100, 100)
			EndIf
		Else
			Color(mb\R, mb\G, mb\B)
		EndIf
		If mb\BigFont Then 
			SetFont(fo\FontID[1])
		Else 
			SetFont(fo\FontID[0])
		EndIf
		Text(mb\x + mb\Width / 2, mb\y + mb\Height / 2, mb\Txt, True, True)
	Next
End Function

Function DrawButton%(x%, y%, Width%, Height%, Txt$, BigFont% = True, WaitForMouseUp% = False, Locked% = False, R% = 255, G% = 255, B% = 255)
	Local mb.MenuButton, currMButton.MenuButton
	Local Clicked% = False
	Local ButtonExists% = False
	
	For mb.MenuButton = Each MenuButton
		If mb\x = x And mb\y = y And mb\Width = Width And mb\Height = Height Then
			ButtonExists = True
			Exit
		EndIf
	Next
	If (Not ButtonExists) Then
		mb.MenuButton = New MenuButton
		mb\x = x
		mb\y = y
		mb\Width = Width
		mb\Height = Height
		mb\Txt = Txt
		mb\BigFont = BigFont
		mb\Locked = Locked
		mb\R = R
		mb\B = B
		mb\G = G
	Else
		currMButton = mb
		currMButton\Txt = Txt
		currMButton\Locked = Locked
	EndIf
	
	If MouseOn(x, y, Width, Height) Then
		If (MouseHit1 And (Not WaitForMouseUp)) Lor (MouseUp1 And WaitForMouseUp) Then
			If Locked Then
				PlaySound_Strict(ButtonSFX2)
			Else
				Clicked = True
				PlaySound_Strict(ButtonSFX)
			EndIf
		EndIf
	EndIf
	
	Return(Clicked)
End Function

Function DrawLauncherButton%(x%, y%, Width%, Height%, Txt$, BigFont% = True, WaitForMouseUp% = False, Locked% = False, R% = 255, G% = 255, B% = 255)
	Local Clicked% = False
	
	DrawFrame(x, y, Width, Height)
	If MouseOn(x, y, Width, Height) Then
		Color(30, 30, 30)
		If (MouseHit1 And (Not WaitForMouseUp)) Lor (MouseUp1 And WaitForMouseUp) Then 
			If Locked Then
				PlaySound_Strict(ButtonSFX2)
			Else
				Clicked = True
				PlaySound_Strict(ButtonSFX)
			EndIf
		EndIf
		Rect(x + 4, y + 4, Width - 8, Height - 8)	
	Else
		Color(0, 0, 0)
	EndIf
	
	If Locked Then
		If R <> 255 Lor G <> 255 Lor B <> 255 Then
			Color(R, G, B)
		Else
			Color(100, 100, 100)
		EndIf
	Else
		Color(R, G, B)
	EndIf
	If BigFont Then
		SetFont(fo\FontID[1])
	Else
		SetFont(fo\FontID[0])
	EndIf
	Text(x + Width / 2, y + Height / 2, Txt, True, True)
	
	Return(Clicked)
End Function

Type MenuTick
	Field x%, y%
	Field Selected%
	Field Locked%
End Type

Function RenderMenuTicks()
	Local mt.MenuTick
	Local Width%, Height%
	
	For mt.MenuTick = Each MenuTick
		Width = 20 * MenuScale
		Height = 20 * MenuScale
		
		Color(255, 255, 255)
		DrawTiledImageRect(MenuWhite, (mt\x Mod 256), (mt\y Mod 256), 512, 512, mt\x, mt\y, Width, Height)
		
		Local Highlight% = MouseOn(mt\x, mt\y, Width, Height)
		
		If Highlight Then
			Color(50, 50, 50)
		Else
			Color(0, 0, 0)		
		EndIf
		
		Rect(mt\x + 2, mt\y + 2, Width - 4, Height - 4)
		
		If mt\Selected Then
			If Highlight Then
				Color(255, 255, 255)
			Else
				Color(200, 200, 200)
			EndIf
			DrawTiledImageRect(MenuWhite, (mt\x Mod 256), (mt\y Mod 256), 512, 512, mt\x + 4, mt\y + 4, Width - 8, Height - 8)
		EndIf
		
		Color(255, 255, 255)
	Next
End Function

Function DrawTick%(x%, y%, Selected%, Locked% = False)
	Local mt.MenuTick, currTick.MenuTick
	Local TickExists% = False
	Local Width% = 20 * MenuScale, Height% = 20 * MenuScale
	
	For mt.MenuTick = Each MenuTick
		If mt\x = x And mt\y = y Then
			TickExists = True
			Exit
		EndIf
	Next
	If (Not TickExists) Then
		mt.MenuTick = New MenuTick
		mt\x = x
		mt\y = y
		mt\Selected = Selected
		mt\Locked = Locked
	Else
		currTick = mt
		mt\Selected = Selected
		mt\Locked = Locked
	EndIf
	
	Local Highlight% = MouseOn(x, y, Width, Height)
	
	If Highlight Then
		If MouseHit1 Then 
			If Locked Then
				PlaySound_Strict(ButtonSFX2)
			Else
				Selected = (Not Selected)
				PlaySound_Strict(ButtonSFX)
			EndIf
		EndIf
	EndIf
	
	Return(Selected)
End Function

Function DrawLauncherTick%(x%, y%, Selected%, Locked% = False)
	Local Width% = 20 * MenuScale, Height% = 20 * MenuScale
	Local Highlight% = MouseOn(x, y, Width, Height)
	
	Color(255, 255, 255)
	DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x, y, Width, Height)
	
	If Highlight Then
		If Locked Then
			Color(0, 0, 0)
			If MouseHit1 Then PlaySound_Strict(ButtonSFX2)
		Else
			Color(50, 50, 50)
			If MouseHit1 Then Selected = (Not Selected) : PlaySound_Strict(ButtonSFX)
		EndIf
	Else
		Color(0, 0, 0)		
	EndIf
	
	Rect(x + 2, y + 2, Width - 4, Height - 4)
	
	If Selected Then
		If Highlight Then
			Color(255, 255, 255)
		Else
			Color(200, 200, 200)
		EndIf
		DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x + 4, y + 4, Width - 8, Height - 8)
	EndIf
	
	Color(255, 255, 255)
	
	Return(Selected)
End Function

Function rInput$(aString$, MaxChr%)
	Local Value% = GetKey()
	Local Length% = Len(aString)
	
	If CursorPos = -1 Then CursorPos = Length
	
	If KeyDown(29) Then
		If Value = 30 Then CursorPos = Length
		If Value = 31 Then CursorPos = 0
		If Value = 22 Then
			aString = Left(aString, CursorPos) + GetClipboardContents() + Right(aString, Length - CursorPos)
			CursorPos = CursorPos + Len(aString) - Length
			If MaxChr > 0 And MaxChr < Len(aString) Then aString = Left(aString, MaxChr) : CursorPos = MaxChr
		EndIf
		Return(aString)
	EndIf
	
	If Value = 30 Then
		CursorPos = Min(CursorPos + 1, Length)
	ElseIf Value = 31
		CursorPos = Max(CursorPos - 1, 0)
	Else
		aString = TextInput(Left(aString, CursorPos)) + Mid(aString, CursorPos + 1)
		CursorPos = CursorPos + Len(aString) - Length
		If MaxChr > 0 And MaxChr < Len(aString) Then aString = Left(aString, MaxChr) : CursorPos = MaxChr
	EndIf
	Return(aString)
End Function

Type MenuInputBox
	Field x%, y%, Width%, Height%
	Field Txt$
	Field ID%
End Type

Function RenderMenuInputBoxes()
	Local mib.MenuInputBox
	
	For mib.MenuInputBox = Each MenuInputBox
		Color(255, 255, 255)
		DrawTiledImageRect(MenuWhite, (mib\x Mod 256), (mib\y Mod 256), 512, 512, mib\x, mib\y, mib\Width, mib\Height)
		Color(0, 0, 0)
		
		If MouseOn(mib\x, mib\y, mib\Width, mib\Height) Then
			Color(50, 50, 50)
		EndIf
		
		Rect(mib\x + 2, mib\y + 2, mib\Width - 4, mib\Height - 4)
		Color(255, 255, 255)	
		
		If SelectedInputBox = mib\ID Then
			If (MilliSecs2() Mod 800) < 400 Then Rect(mib\x + mib\Width / 2 - (StringWidth(mib\Txt)) / 2 + StringWidth(Left(mib\Txt, CursorPos)), mib\y + mib\Height / 2 - 5, 2, 12)
		EndIf	
		
		Text(mib\x + mib\Width / 2, mib\y + mib\Height / 2, mib\Txt, True, True)
	Next
End Function

Function InputBox$(x%, y%, Width%, Height%, Txt$, ID% = 0, MaxChr% = 0)
	Local mib.MenuInputBox, currInputBox.MenuInputBox
	Local InputBoxExists% = False
	
	For mib.MenuInputBox = Each MenuInputBox
		If mib\x = x And mib\y = y And mib\Width = Width And mib\Height = Height Then
			InputBoxExists = True
			Exit
		EndIf
	Next
	If (Not InputBoxExists) Then
		mib.MenuInputBox = New MenuInputBox
		mib\x = x
		mib\y = y
		mib\Width = Width
		mib\Height = Height
		mib\Txt = Txt
		mib\ID = ID
	Else
		currInputBox = mib
		currInputBox\Txt = Txt
	EndIf
	
	Local MouseOnBox% = False
	
	If MouseOn(x, y, Width, Height) Then
		MouseOnBox = True
		If MouseHit1 Then SelectedInputBox = ID : FlushKeys() : CursorPos = -1
	EndIf
	
	If (Not MouseOnBox) And MouseHit1 And SelectedInputBox = ID Then SelectedInputBox = 0 : CursorPos = -1
	
	If SelectedInputBox = ID Then
		Txt = rInput(Txt, MaxChr)
	EndIf	
	
	Return(Txt)
End Function

Type MenuSlideBar
	Field x%, y%, Width%
	Field Value#
	Field TextLeft$
	Field TextRight$
End Type

Function RenderMenuSlideBars()
	Local msb.MenuSlideBar
	
	For msb.MenuSlideBar = Each MenuSlideBar
		Color(255, 255, 255)
		Rect(msb\x, msb\y, msb\Width + 14, 20, False)
		
		DrawImage(BlinkMeterIMG, msb\x + msb\Width * msb\Value / 100.0 + 3, msb\y + 3)
		
		Color(170, 170, 170)
		Text(msb\x - 50 * MenuScale, msb\y + 4 * MenuScale, msb\TextLeft)					
		Text(msb\x + msb\Width + 38 * MenuScale, msb\y + 4 * MenuScale, msb\TextRight)	
	Next
End Function

Function SlideBar#(x%, y%, Width%, Value#, TextLeft$ = "LOW", TextRight$ = "HIGH")
	Local msb.MenuSlideBar, currSlideBar.MenuSlideBar
	Local SlideBarExists% = False
	
	For msb.MenuSlideBar = Each MenuSlideBar
		If msb\x = x And msb\y = y And msb\Width = Width Then
			SlideBarExists = True
			Exit
		EndIf
	Next
	If (Not SlideBarExists) Then
		msb.MenuSlideBar = New MenuSlideBar
		msb\x = x
		msb\y = y
		msb\Width = Width
		msb\Value = Value
		msb\TextLeft = TextLeft
		msb\TextRight = TextRight
	Else
		currSlideBar = msb
		currSlideBar\Value = Value
	EndIf
	
	If MouseDown1 And OnSliderID = 0 Then
		If ScaledMouseX() >= x And ScaledMouseX() =< x + Width + 14 And ScaledMouseY() >= y And ScaledMouseY() =< y + 20 Then
			Value = Min(Max((ScaledMouseX() - x) * 100 / Width, 0), 100)
		EndIf
	EndIf
	
	Return(Value)
End Function

Global OnSliderID% = 0

Type MenuSlider
	Field x%, y%, Width%
	Field Value%
	Field ID%
	Field Val1$, Val2$, Val3$, Val4$, Val5$
	Field Amount%
End Type

Function RenderMenuSliders()
	Local ms.MenuSlider
	
	For ms.MenuSlider = Each MenuSlider
		If ms\Amount = 3
			Color(200, 200, 200)
			Rect(ms\x, ms\y, ms\Width + 14, 10, True)
			Rect(ms\x, ms\y - 8, 4, 14, True)
			Rect(ms\x + (ms\Width / 2) + 5, ms\y - 8, 4, 14, True)
			Rect(ms\x + ms\Width + 10, ms\y - 8, 4, 14, True)
			
			If ms\ID = OnSliderID Then
				Color(0, 255, 0)
				Rect(ms\x, ms\y, ms\Width + 14, 10, True)
			Else
				If ScaledMouseX() >= ms\x And ScaledMouseX() =< ms\x + ms\Width + 14 And ScaledMouseY() >= ms\y - 8 And ScaledMouseY() =< ms\y + 10
					Color(0, 200, 0)
					Rect(ms\x, ms\y, ms\Width + 14, 10, False)
				EndIf
			EndIf
			
			If ms\Value = 0 Then
				DrawImage(BlinkMeterIMG, ms\x, ms\y - 8)
			ElseIf ms\Value = 1
				DrawImage(BlinkMeterIMG, ms\x + (ms\Width / 2) + 3, ms\y - 8)
			Else
				DrawImage(BlinkMeterIMG, ms\x + ms\Width + 6, ms\y - 8)
			EndIf
			
			Color(170, 170, 170)
			If ms\Value = 0 Then
				Text(ms\x + 2, ms\y + 10 + MenuScale, ms\Val1, True)
			ElseIf ms\Value = 1
				Text(ms\x + (ms\Width / 2) + 7, ms\y + 10 + MenuScale, ms\Val2, True)
			Else
				Text(ms\x + ms\Width + 12, ms\y + 10 + MenuScale, ms\Val3, True)
			EndIf
		ElseIf ms\Amount = 5
			Color(200, 200, 200)
			Rect(ms\x, ms\y, ms\Width + 14, 10, True)
			Rect(ms\x, ms\y - 8, 4, 14, True)
			Rect(ms\x + (ms\Width / 4) + 2.5, ms\y - 8, 4, 14, True)
			Rect(ms\x + (ms\Width / 2) + 5, ms\y - 8, 4, 14, True)
			Rect(ms\x + (ms\Width * 0.75) + 7.5, ms\y - 8, 4, 14, True)
			Rect(ms\x + ms\Width + 10, ms\y - 8, 4, 14, True)
			
			If ms\ID = OnSliderID Then
				Color(0, 255, 0)
				Rect(ms\x, ms\y, ms\Width + 14, 10, True)
			Else
				If (ScaledMouseX() >= ms\x) And (ScaledMouseX() =< ms\x + ms\Width + 14) And (ScaledMouseY() >= ms\y - 8) And (ScaledMouseY() =< ms\y + 10)
					Color(0, 200, 0)
					Rect(ms\x, ms\y, ms\Width + 14, 10, False)
				EndIf
			EndIf
			
			If ms\Value = 0 Then
				DrawImage(BlinkMeterIMG, ms\x, ms\y - 8)
			ElseIf ms\Value = 1
				DrawImage(BlinkMeterIMG, ms\x + (ms\Width / 4) + 1.5, ms\y - 8)
			ElseIf ms\Value = 2
				DrawImage(BlinkMeterIMG, ms\x + (ms\Width / 2) + 3, ms\y - 8)
			ElseIf ms\Value = 3
				DrawImage(BlinkMeterIMG, ms\x + (ms\Width * 0.75) + 4.5, ms\y - 8)
			Else
				DrawImage(BlinkMeterIMG, ms\x + ms\Width + 6, ms\y - 8)
			EndIf
			
			Color(170, 170, 170)
			If ms\Value = 0 Then
				Text(ms\x + 2, ms\y + 10 + MenuScale, ms\Val1, True)
			ElseIf ms\Value = 1
				Text(ms\x + (ms\Width / 4) + 4.5, ms\y + 10 + MenuScale, ms\Val2, True)
			ElseIf ms\Value = 2
				Text(ms\x + (ms\Width / 2) + 7, ms\y + 10 + MenuScale, ms\Val3, True)
			ElseIf ms\Value = 3
				Text(ms\x + (ms\Width * 0.75) + 9.5, ms\y + 10 + MenuScale, ms\Val4, True)
			Else
				Text(ms\x + ms\Width + 12, ms\y + 10 + MenuScale, ms\Val5, True)
			EndIf
		EndIf
	Next
End Function

Function Slider3(x%, y%, Width%, Value%, ID%, Val1$, Val2$, Val3$)
	Local ms.MenuSlider, currSlider.MenuSlider
	Local Slider3Exists% = False
	
	For ms.MenuSlider = Each MenuSlider
		If ms\x = x And ms\y = y And ms\Width = Width And ms\Amount = 3 Then
			Slider3Exists = True
			Exit
		EndIf
	Next
	If (Not Slider3Exists) Then
		ms.MenuSlider = New MenuSlider
		ms\x = x
		ms\y = y
		ms\Width = Width
		ms\ID = ID
		ms\Value = Value
		ms\Val1 = Val1
		ms\Val2 = Val2
		ms\Val3 = Val3
		ms\Amount = 3
	Else
		currSlider = ms
		currSlider\Value = Value
	EndIf
	
	If MouseDown1 Then
		If ScaledMouseX() >= x And ScaledMouseX() =< x + Width + 14 And ScaledMouseY() >= y - 8 And ScaledMouseY() =< y + 10
			OnSliderID = ID
		EndIf
	EndIf
	
	If ID = OnSliderID Then
		If ScaledMouseX() =< x + 8
			Value = 0
		ElseIf ScaledMouseX() >= x + Width / 2 And ScaledMouseX() =< x + (Width / 2) + 8
			Value = 1
		ElseIf ScaledMouseX() >= x + Width
			Value = 2
		EndIf
	EndIf
	
	Return(Value)
End Function

Function Slider5(x%, y%, Width%, Value%, ID%, Val1$, Val2$, Val3$, Val4$, Val5$)
	Local ms.MenuSlider, currSlider.MenuSlider
	Local Slider5Exists% = False
	
	For ms.MenuSlider = Each MenuSlider
		If ms\x = x And ms\y = y And ms\Width = Width And ms\Amount = 5 Then
			Slider5Exists = True
			Exit
		EndIf
	Next
	If (Not Slider5Exists) Then
		ms.MenuSlider = New MenuSlider
		ms\x = x
		ms\y = y
		ms\Width = Width
		ms\ID = ID
		ms\Value = Value
		ms\Val1 = Val1
		ms\Val2 = Val2
		ms\Val3 = Val3
		ms\Val4 = Val4
		ms\Val5 = Val5
		ms\Amount = 5
	Else
		currSlider = ms
		currSlider\Value = Value
	EndIf
	
	If MouseDown1 Then
		If ScaledMouseX() >= x And ScaledMouseX() =< x + Width + 14 And ScaledMouseY() >= y - 8 And ScaledMouseY() =< y + 10
			OnSliderID = ID
		EndIf
	EndIf
	
	If ID = OnSliderID Then
		If (ScaledMouseX() =< x + 8)
			Value = 0
		ElseIf ScaledMouseX() >= x + Width / 4 And ScaledMouseX() =< x + (Width / 4) + 8
			Value = 1
		ElseIf ScaledMouseX() >= x + Width / 2 And ScaledMouseX() =< x + (Width / 2) + 8
			Value = 2
		ElseIf ScaledMouseX() >= x + Width * 0.75 And ScaledMouseX() =< x + (Width * 0.75) + 8
			Value = 3
		ElseIf ScaledMouseX() >= x + Width
			Value = 4
		EndIf
	EndIf
	
	Return(Value)
End Function

Function DeleteMenuGadgets()
	Delete Each MenuButton
	Delete Each MenuTick
	Delete Each MenuInputBox
	Delete Each MenuSlideBar
	Delete Each MenuSlider
End Function

Function RowText(A$, x%, y%, W%, H%, Align% = 0, Leading# = 1.0)
	; ~ Display A$ starting at x, y - no wider than W and no taller than H (all in pixels)
	; ~ Leading is optional extra vertical spacing in pixels
	
	If H < 1 Then H = 2048
	
	Local LinesShown% = 0
	Local Height% = StringHeight(A) + Leading
	Local b$
	
	While Len(A) > 0
		Local Space$ = Instr(A, " ")
		
		If Space = 0 Then Space = Len(A$)
		
		Local Temp$ = Left(A, Space)
		Local Trimmed$ = Trim(Temp) ; ~ We might ignore a final space 
		Local Extra% = 0 ; ~ We haven't ignored it yet
		
		; ~ Ignore final space if doing so would make a word fit at end of line:
		If (StringWidth (b + Temp) > W) And (StringWidth(b + Trimmed) =< W) Then
			Temp = Trimmed
			Extra = 1
		EndIf
		
		If StringWidth(b + Temp) > W Then ; ~ Too big, so print what will fit
			If Align Then
				Text(x + W / 2 - (StringWidth(b) / 2), LinesShown * Height + y, b)
			Else
				Text(x, LinesShown * Height + y, b)
			EndIf
			
			LinesShown = LinesShown + 1
			b = ""
		Else ; ~ Append it to b$ (which will eventually be printed) and remove it from A$
			b = b + Temp
			A = Right(A, Len(A) - (Len(Temp) + Extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ; ~ The next line would be too tall, so leave
	Wend
	
	If (b <> "") And ((LinesShown + 1) =< H) Then
		If Align Then
			Text(x + W / 2 - (StringWidth(b) / 2), LinesShown * Height + y, b) ; ~ Print any remaining text if it'll fit vertically
		Else
			Text(x, LinesShown * Height + y, b) ; ~ Print any remaining text if it'll fit vertically
		EndIf
	EndIf
End Function

Function GetLineAmount(A$, W%, H%, Leading# = 1.0)
	; ~ Display A$ no wider than W and no taller than H (all in pixels)
	; ~ Leading is optional extra vertical spacing in pixels
	
	If H < 1 Then H = 2048
	
	Local LinesShown% = 0
	Local Height% = StringHeight(A) + Leading
	Local b$
	
	While Len(A) > 0
		Local Space$ = Instr(A, " ")
		
		If Space = 0 Then Space = Len(A)
		
		Local Temp$ = Left(A, Space)
		Local Trimmed$ = Trim(Temp) ; ~ We might ignore a final space 
		Local Extra% = 0 ; ~ We haven't ignored it yet
		
		; ~ Ignore final space if doing so would make a word fit at end of line:
		If (StringWidth(b + Temp) > W) And (StringWidth(b + Trimmed) =< W) Then
			Temp = Trimmed
			Extra = 1
		EndIf
		
		If StringWidth(b + Temp) > W Then ; ~ Too big, so print what will fit
			LinesShown = LinesShown + 1
			b = ""
		Else ; ~ Append it to b$ (which will eventually be printed) and remove it from A$
			b = b + Temp
			A = Right(A, Len(A) - (Len(Temp) + Extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ; ~ The next line would be too tall, so leave
	Wend
	
	Return(LinesShown + 1)
End Function

Function DrawOptionsTooltip(x%, y%, Width%, Height%, Option$, Value# = 0.0, InGame% = False)
	Local fX# = x + 6.0 * MenuScale
	Local fY# = y + 6.0 * MenuScale
	Local fW# = Width - 12.0 * MenuScale
	Local fH# = Height - 12.0 * MenuScale
	Local Lines% = 0, Lines2% = 0
	Local Txt$ = ""
	Local Txt2$ = "", R% = 0, G% = 0, B% = 0
	
	SetFont(fo\FontID[0])
	Color(255, 255, 255)
	Select Lower(Option)
		Case "bump"
			;[Block]
			Txt = Chr(34) + "Bump mapping" + Chr(34) + " is used to simulate bumps and dents by distorting the lightmaps."
			Txt2 = "This option cannot be changed in-game."
			R = 255
			;[End Block]
		Case "vsync"
			;[Block]
			Txt = Chr(34) + "Vertical sync" + Chr(34) + " waits for the display to finish its current refresh cycle before calculating the next frame, preventing issues such as "
			Txt = Txt + "screen tearing. This ties the game's frame rate to your display's refresh rate and may cause some input lag."
			;[End Block]
		Case "antialias"
			;[Block]
			Txt = Chr(34) + "Anti-Aliasing" + Chr(34) + " is used to smooth the rendered image before displaying in order to reduce aliasing around the edges of models."
			Txt2 = "This option only takes effect in fullscreen."
			R = 255
			;[End Block]
		Case "roomlights"
			;[Block]
			Txt = "Toggles the artificial lens flare effect generated over specific light sources."
			;[End Block]
		Case "gamma"
			;[Block]
			Txt = Chr(34) + "Gamma correction" + Chr(34) + " is used to achieve a good brightness factor to balance out your display's gamma if the game appears either too dark or bright. "
			Txt = Txt + "Setting it too high or low can cause the graphics to look less detailed."
			R = 255
			G = 255
			B = 255
			Txt2 = "Current value: " + Int(Value * 100.0) + "% (default is 100%)"
			;[End Block]
		Case "texquality"
			;[Block]
			Txt = Chr(34) + "Texture LOD Bias" + Chr(34) + " affects the distance at which texture detail will change to prevent aliasing. Change this option if textures flicker or look too blurry."
			;[End Block]
		Case "particleamount"
			;[Block]
			Txt = "Determines the amount of particles that can be rendered per tick."
			Select Value
				Case 0
					;[Block]
					R = 255
					Txt2 = "Only smoke emitters will produce particles."
					;[End Block]
				Case 1
					;[Block]
					R = 255
					G = 255
					Txt2 = "Only a few particles will be rendered per tick."
					;[End Block]
				Case 2
					;[Block]
					G = 255
					Txt2 = "All particles are rendered."
					;[End Block]
			End Select
			;[End Block]
		Case "vram"
			;[Block]
			Txt = "Textures that are stored in the Video-RAM will load faster, but this also has negative effects on the texture quality as well."
			Txt2 = "This option cannot be changed in-game."
			R = 255
			;[End Block]
		Case "musicvol"
			;[Block]
			Txt = "Adjusts the volume of background music. Sliding the bar fully to the left will mute all music."
			R = 255
			G = 255
			B = 255
			Txt2 = "Current value: " + Int(Value * 100.0) + "% (default is 50%)"
			;[End Block]
		Case "soundvol"
			;[Block]
			Txt = "Adjusts the volume of sound effects. Sliding the bar fully to the left will mute all sounds."
			R = 255
			G = 255
			B = 255
			Txt2 = "Current value: " + Int(Value * 100.0) + "% (default is 50%)"
			;[End Block]
		Case "sfxautorelease"
			;[Block]
			Txt = Chr(34)+"Sound auto-release" + Chr(34) + " will free a sound from memory if it not used after 5 seconds. Prevents memory allocation issues."
			R = 255
			Txt2 = "This option cannot be changed in-game."
			;[End Block]
		Case "usertrack"
			;[Block]
			Txt = "Toggles the ability to play custom tracks over channel 1 of the radio. These tracks are loaded from the " + Chr(34) + "SFX\Radio\UserTracks\" + Chr(34)
			Txt = Txt + " directory. Press " + Chr(34) + "1" + Chr(34) + " when the radio is selected to change track."
			R = 255
			Txt2 = "This option cannot be changed in-game."
			;[End Block]
		Case "usertrackmode"
			;[Block]
			Txt = "Sets the playing mode for the custom tracks. " + Chr(34) + "Repeat" + Chr(34) + " plays every file in alphabetical order. " + Chr(34) + "Random" + Chr(34) + " chooses the "
			Txt = Txt + "next track at random."
			R = 255
			G = 255
			Txt2 = "Note that the random mode does not prevent previously played tracks from repeating."
			;[End Block]
		Case "usertrackscan"
			;[Block]
			Txt = "Re-checks the user tracks directory for any new or removed sound files."
			;[End Block]
		Case "mousesensitivity"
			;[Block]
			Txt = "Adjusts the speed of the mouse pointer."
			R = 255
			G = 255
			B = 255
			Txt2 = "Current value: " + Int((0.5 + Value) * 100.0) + "% (default is 50%)"
			;[End Block]
		Case "mouseinvert"
			;[Block]
			Txt = Chr(34)+"Invert mouse Y-axis" + Chr(34) + " is self-explanatory."
			;[End Block]
		Case "mousesmoothing"
			;[Block]
			Txt = "Adjusts the amount of smoothing of the mouse pointer."
			R = 255
			G = 255
			B = 255
			Txt2 = "Current value: " + Int(Value * 100.0) + "% (default is 100%)"
			;[End Block]
		Case "controls"
			;[Block]
			Txt = "Configure the in-game control scheme."
			;[End Block]
		Case "hud"
			;[Block]
			Txt = "Display the blink and stamina meters."
			;[End Block]
		Case "consoleenable"
			;[Block]
			Txt = "Toggles the use of the developer console. Can be used in-game by pressing " + key\Name[key\CONSOLE] + "."
			;[End Block]
		Case "consoleerror"
			;[Block]
			Txt = Chr(34) + "Open console on error" + Chr(34) + " is self-explanatory."
			;[End Block]
		Case "achpopup"
			;[Block]
			Txt = "Displays a pop-up notification when an achievement is unlocked."
			;[End Block]
		Case "showfps"
			;[Block]
			Txt = "Displays the frames per second counter at the top left-hand corner."
			;[End Block]
		Case "framelimit"
			;[Block]
			Txt = "Limits the frame rate that the game can run at to a desired value."
			If Value > 0 And Value < 60 Then
				R = 255
				G = 255
				Txt2 = "Usually, 60 FPS or higher is preferred. If you are noticing excessive stuttering at this setting, try lowering it to make your framerate more consistent."
			EndIf
			;[End Block]
		Case "playstartup"
			;[Block]
			Txt = Chr(34) + "Play startup videos" + Chr(34) + " is self-explanatory."
			R = 255
			Txt2 = "This option cannot be changed in-game."
			;[End Block]
		Case "uselauncher"
			;[Block]
			Txt = Chr(34) + "Use launcher" + Chr(34) + " is self-explanatory."
			R = 255
			Txt2 = "This option cannot be changed in-game."
			;[End Block]
		Case "fov"
			;[Block]
			Txt = Chr(34) + "Field of view" + Chr(34) + " is the amount of game view that is on display during a game."
			Txt2 = "Current value: " + Int(FOV) + "� (default is 74�)"
			;[End Block]
		Case "barstyle"
			;[Block]
		    Txt = "Changes the Bar's style to Dynamic or Classic one."
			R = 255
		    Txt2 = "This option cannot be changed in-game."
			;[End Block]
	End Select
	
	Lines = GetLineAmount(Txt, fW, fH)
	If Txt2 = ""
		DrawFrame(x, y, Width, ((StringHeight(Txt) * Lines) + (10 + Lines) * MenuScale))
	Else
		Lines2 = GetLineAmount(Txt2, fW, fH)
		DrawFrame(x, y, Width, (((StringHeight(Txt) * Lines) + (10 + Lines) * MenuScale) + (StringHeight(Txt2) * Lines2) + (10 + Lines2) * MenuScale))
	EndIf
	RowText(Txt, fX, fY, fW, fH)
	If Txt2 <> "" Then
		Color(R, G, B)
		RowText(Txt2, fX, (fY + (StringHeight(Txt) * Lines) + (5 + Lines) * MenuScale), fW, fH)
	EndIf
End Function

Function DrawMapCreatorTooltip(x%, y%, Width%, Height%, MapName$)
	Local fX# = x + 6.0 * MenuScale
	Local fY# = y + 6.0 * MenuScale
	Local fW# = Width - 12.0 * MenuScale
	Local fH# = Height - 12.0 * MenuScale
	Local Lines% = 0
	
	SetFont(fo\FontID[0])
	Color(255, 255, 255)
	
	Local Txt$[6]
	
	If Right(MapName, 6) = "cbmap2" Then
		Txt[0] = Left(MapName, Len(MapName) - 7)
		
		Local f% = OpenFile("Map Creator\Maps\" + MapName)
		
		Local Author$ = ReadLine(f)
		Local Descr$ = ReadLine(f)
		
		ReadByte(f)
		ReadByte(f)
		
		Local rAmount% = ReadInt(f)
		
		If ReadInt(f) > 0 Then
			Local HasForest% = True
		Else
			HasForest = False
		EndIf
		If ReadInt(f) > 0 Then
			Local HasMT% = True
		Else
			HasMT = False
		EndIf
		
		CloseFile(f)
	Else
		Txt[0] = Left(MapName, Len(MapName) - 6)
		Author = "[Unknown]"
		Descr = "[No description]"
		rAmount = 0
		HasForest = False
		HasMT = False
	EndIf
	Txt[1] = "Made by: " + Author
	Txt[2] = "Description: " + Descr
	If rAmount > 0 Then
		Txt[3] = "Room amount: " + rAmount
	Else
		Txt[3] = "Room amount: [Unknown]"
	EndIf
	If HasForest Then
		Txt[4] = "Has custom forest: Yes"
	Else
		Txt[4] = "Has custom forest: No"
	EndIf
	If HasMT Then
		Txt[5] = "Has custom maintenance tunnel: Yes"
	Else
		Txt[5] = "Has custom maintenance tunnel: No"
	EndIf
	
	Lines = GetLineAmount(Txt[2], fW, fH)
	DrawFrame(x, y, Width, (StringHeight(Txt[0]) * 6) + StringHeight(Txt[2]) * Lines + 5 * MenuScale)
	
	Color(255, 255, 255)
	Text(fX, fY,Txt[0])
	Text(fX, fY + StringHeight(Txt[0]), Txt[1])
	RowText(Txt[2], fX, fY + (StringHeight(Txt[0]) * 2), fW, fH)
	Text(fX, fY + ((StringHeight(Txt[0]) * 2) + StringHeight(Txt[2]) * Lines + 5 * MenuScale), Txt[3])
	Text(fX, fY + ((StringHeight(Txt[0]) * 3) + StringHeight(Txt[2]) * Lines + 5 * MenuScale), Txt[4])
	Text(fX, fY + ((StringHeight(Txt[0]) * 4) + StringHeight(Txt[2]) * Lines + 5 * MenuScale), Txt[5])
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D