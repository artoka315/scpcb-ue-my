Global Curr173.NPCs, Curr106.NPCs, Curr096.NPCs, Curr513_1.NPCs, Curr049.NPCs

; ~ NPC IDs Constants
;[Block]
; ~ Main
Const NPCtype008_1% = 0, NPCtype035_Tentacle% = 1, NPCtype049% = 2, NPCtype049_2% = 3, NPCtype066% = 4, NPCtype096% = 5
Const NPCtype106% = 6, NPCtype173% = 7, NPCtype372% = 8, NPCtype513_1% = 9, NPCtype860_2% = 10, NPCtype939% = 11
Const NPCtype966% = 12, NPCtype1499_1% = 13

Const NPCtypeApache% = 14, NPCtypeClerk% = 15, NPCtypeD% = 16, NPCtypeGuard% = 17, NPCtypeMTF% = 18, NPCtypeVehicle% = 19

; ~ Placeholder
Const NPCtype035% = 20, NPCtype205_Demon% = 21, NPCtype205_Demon2% = 22, NPCtype205_Demon3% = 23, NPCtype205_Woman% = 24
Const NPCtype1048% = 25, NPCtype1048_A% = 26

Const NPCtypeDuck% = 27, NPCtypeCI% = 28, NPCtypeNazi% = 29

; ~ Objects
Const NPCtypeApache_Rotor% = 30, NPCtypeApache_Rotor2% = 31, NPCtype173_Box% = 32, NPCtype682_Arm% = 33
;[End Block]

Type NPCs
	Field OBJ%, OBJ2%, OBJ3%, OBJ4%, Collider%
	Field NPCtype%, ID%
	Field DropSpeed#, Gravity%
	Field State#, State2#, State3#, PrevState%
	Field MakingNoise%
	Field Frame#
	Field Angle#
	Field Sound%, SoundCHN%, SoundTimer#
	Field Sound2%, SoundCHN2%
	Field Speed#, CurrSpeed#
	Field Texture$
	Field Idle#
	Field Reload#
	Field LastSeen%, LastDist#
	Field PrevX#, PrevY#, PrevZ#
	Field Target.NPCs, TargetID%
	Field EnemyX#, EnemyY#, EnemyZ#
	Field Path.WayPoints[21], PathStatus%, PathTimer#, PathLocation%
	Field NVX#, NVY#, NVZ#, NVName$
	Field GravityMult# = 1.0
	Field MaxGravity# = 0.2
	Field MTFVariant%
	Field MTFLeader.NPCs
	Field IsDead%
	Field BlinkTimer# = 1.0
	Field IgnorePlayer%
	Field ManipulateBone%
	Field ManipulationType%
	Field BoneToManipulate$
	Field BonePitch#
	Field BoneYaw#
	Field BoneRoll#
	Field NPCNameInSection$
	Field InFacility% = True
	Field HP%
	Field PathX#, PathZ#
	Field Model$
	Field ModelScaleX#, ModelScaleY#, ModelScaleZ#
	Field HideFromNVG
	Field TextureID% = -1
	Field CollRadius#
	Field IdleTimer#
	Field SoundCHN_IsStream%, SoundCHN2_IsStream%
	Field FallingPickDistance#
	Field UseEarphones% = False
	Field Contained% = False
End Type

Const NPCsFile$ = "Data\NPCs.ini"

Function CreateNPC.NPCs(NPCtype%, x#, y#, z#)
	Local n.NPCs = New NPCs, n2.NPCs
	Local Temp#, i%, Tex%, TexFestive%
	Local SF%, b%, t1%
	
	n\NPCtype = NPCtype
	n\GravityMult = 1.0
	n\MaxGravity = 0.2
	n\CollRadius = 0.2
	n\FallingPickDistance = 10.0
	Select NPCtype
		Case NPCtype173
			;[Block]
			n\NVName = "SCP-173"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.23, 0.32)
			EntityType(n\Collider, HIT_PLAYER)
			n\Gravity = True
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype173])
			
			; ~ On Halloween set Jack-o'-lantern texture
			If (Left(CurrentDate(), 7) = "31 Oct ") Then
				tt\MiscTextureID[14] = True
				TexFestive = LoadTexture_Strict("GFX\npcs\scp_173_H.png")
				EntityTexture(n\OBJ, TexFestive, 0, 0)
				DeleteSingleTextureEntryFromCache(TexFestive)
			EndIf
			
			; ~ On New Year set cookie texture
			If (Left(CurrentDate(), 7) = "01 Jan ") Then
				tt\MiscTextureID[15] = True
				TexFestive = LoadTexture_Strict("GFX\npcs\scp_173_NY.png")
				EntityTexture(n\OBJ, TexFestive, 0, 0)
				DeleteSingleTextureEntryFromCache(TexFestive)
			EndIf
			
			Temp = GetINIFloat(NPCsFile, "SCP-173", "Scale") / MeshDepth(n\OBJ)			
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-173", "Speed") / 100.0
			
			n\OBJ2 = CopyEntity(o\NPCModelID[NPCtype173_Box])
			ScaleEntity(n\OBJ2, RoomScale, RoomScale, RoomScale)
			HideEntity(n\OBJ2)
			
			n\CollRadius = 0.32
			;[End Block]
		Case NPCtype106
			;[Block]
			n\NVName = "SCP-106"
			n\Collider = CreatePivot()
			n\GravityMult = 0.0
			n\MaxGravity = 0.0
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype106])
			
			Temp = GetINIFloat(NPCsFile, "SCP-106", "Scale") / 2.2
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-106", "Speed") / 100.0
			
			Tex = LoadTexture_Strict("GFX\npcs\scp_106_eyes.png", 1, 2)
			n\OBJ2 = CreateSprite()
			ScaleSprite(n\OBJ2, 0.03, 0.03)
			EntityTexture(n\OBJ2, Tex)
			EntityBlend(n\OBJ2, 3)
			EntityFX(n\OBJ2, 1 + 8)
			SpriteViewMode(n\OBJ2, 2)
			DeleteSingleTextureEntryFromCache(Tex)
			;[End Block]
		Case NPCtypeGuard
			;[Block]
			n\NVName = "Human"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtypeGuard])
			
			Temp = GetINIFloat(NPCsFile, "Guard", "Scale") / 2.5
			
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0)
			
			n\Speed = GetINIFloat(NPCsFile, "Guard", "Speed") / 100.0
			;[End Block]
		Case NPCtypeMTF
			;[Block]
			n\NVName = "Human"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtypeMTF])
			
			Temp = GetINIFloat(NPCsFile, "MTF", "Scale") / 2.5
			
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0) 
			
			n\Speed = GetINIFloat(NPCsFile, "MTF", "Speed") / 100.0
			
			If (Not MTFSFX[0]) Then
				MTFSFX[0] = LoadSound_Strict("SFX\Character\MTF\Beep.ogg")
				MTFSFX[1] = LoadSound_Strict("SFX\Character\MTF\Breath.ogg")
			EndIf
			;[End Block]
		Case NPCtypeD
			;[Block]
			n\NVName = "Human"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.32)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtypeD])
			
			Temp = GetINIFloat(NPCsFile, "Class D", "Scale") / MeshWidth(n\OBJ)
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0)
			
			n\Speed = GetINIFloat(NPCsFile, "Class D", "Speed") / 100.0
			
			n\CollRadius = 0.32
			;[End Block]
		Case NPCtype372
			;[Block]
			n\NVName = "SCP-372"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype372])
			
			Temp = GetINIFloat(NPCsFile, "SCP-372", "Scale") / MeshWidth(n\OBJ)
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			;[End Block]
		Case NPCtype513_1
			;[Block]
			n\NVName = "SCP-513-1"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype513_1])
			
			n\OBJ2 = CopyEntity(n\OBJ)
			EntityAlpha(n\OBJ2, 0.6)
			
			Temp = GetINIFloat(NPCsFile, "SCP-513-1", "Scale") / MeshWidth(n\OBJ)
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			ScaleEntity(n\OBJ2, Temp, Temp, Temp)
			;[End Block]
		Case NPCtype096
			;[Block]
			n\NVName = "SCP-096"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.26)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype096])
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-096", "Speed") / 100.0
			
			Temp = GetINIFloat(NPCsFile, "SCP-096", "Scale") / 3.0
			ScaleEntity(n\OBJ, Temp, Temp, Temp)	
			
			MeshCullBox(n\OBJ, (-MeshWidth(n\OBJ)) * 2.0, (-MeshHeight(n\OBJ)) * 2.0, (-MeshDepth(n\OBJ)) * 2.0, MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 4.0, MeshDepth(n\OBJ) * 4.0)
			
			n\OBJ2 = CreateSprite(FindChild(n\OBJ, "Reyelid"))
			ScaleSprite(n\OBJ2, 0.07, 0.08)
			EntityOrder(n\OBJ2, -5)
			EntityTexture(n\OBJ2, tt\OverlayTextureID[6])
			HideEntity(n\OBJ2)
			
			n\CollRadius = 0.26
			;[End Block]
		Case NPCtype049
			;[Block]
			n\NVName = "SCP-049"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype049])
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-049", "Speed") / 100.0
			
			Temp = GetINIFloat(NPCsFile, "SCP-049", "Scale")
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			n\Sound = LoadSound_Strict("SFX\Horror\Horror12.ogg")
			
			If (Not HorrorSFX[13]) Then HorrorSFX[13] = LoadSound_Strict("SFX\Horror\Horror13.ogg")
			;[End Block]
		Case NPCtype049_2
			;[Block]
			n\NVName = "Human"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype049_2])
			
			Temp = GetINIFloat(NPCsFile, "SCP-049-2", "Scale") / 2.5
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0)
			
			SetAnimTime(n\OBJ, 107.0)
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-049-2", "Speed") / 100.0
			
			n\Sound = LoadSound_Strict("SFX\SCP\049_2\Breath.ogg")
			
			n\HP = 100
			;[End Block]
		Case NPCtypeApache
			;[Block]
			n\NVName = "Apache Helicopter"
			n\GravityMult = 0.0
			n\MaxGravity = 0.0
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtypeApache])
			
			n\OBJ2 = CopyEntity(o\NPCModelID[NPCtypeApache_Rotor])
			EntityParent(n\OBJ2, n\OBJ)
			
			For i = -1 To 1 Step 2
				Local Rotor2% = CopyEntity(n\OBJ2, n\OBJ2)
				
				RotateEntity(Rotor2, 0.0, 4.0 * i, 0.0)
				EntityAlpha(Rotor2, 0.5)
			Next
			
			n\OBJ3 = CopyEntity(o\NPCModelID[NPCtypeApache_Rotor2])
			EntityParent(n\OBJ3, n\OBJ)
			PositionEntity(n\OBJ3, 0.0, 2.15, -5.48)
			
			EntityType(n\Collider, HIT_APACHE)
			EntityRadius(n\Collider, 3.0)
			
			For i = -1 To 1 Step 2
				Local Light1% = CreateLight(2, n\OBJ)
				
				LightRange(Light1, 2.0)
				LightColor(Light1, 255, 255, 255)
				PositionEntity(Light1, 1.65 * i, 1.17, -0.25)
				
				Local LightSprite% = CreateSprite(n\OBJ)
				
				PositionEntity(LightSprite, 1.65 * i, 1.17, 0.0, -0.25)
				ScaleSprite(LightSprite, 0.13, 0.13)
				EntityTexture(LightSprite, tt\LightSpriteID[0])
				EntityBlend(LightSprite, 3)
				EntityFX(LightSprite, 1 + 8)				
			Next
			
			Temp = 0.7
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			;[End Block]
		Case NPCtype035_Tentacle
			;[Block]
			n\NVName = "Unidentified"
			
			n\Collider = CreatePivot()
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype035_Tentacle])
			
			Temp = GetINIFloat(NPCsFile, "SCP-035's Tentacle", "Scale") / 10.0
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			SetAnimTime(n\OBJ, 283.0)
			
			n\HP = 500
			;[End Block]
		Case NPCtype860_2
			;[Block]
			n\NVName = "Unidentified"
			
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.25)
			EntityType(n\Collider, HIT_PLAYER)
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype860_2])
			
			EntityFX(n\OBJ, 1)
			
			Tex = LoadTexture_Strict("GFX\npcs\scp_860_2_eyes.png", 1 + 2, 2)
			n\OBJ2 = CreateSprite()
			ScaleSprite(n\OBJ2, 0.1, 0.1)
			EntityTexture(n\OBJ2, Tex)
			DeleteSingleTextureEntryFromCache(Tex)
			
			EntityFX(n\OBJ2, 1 + 8)
			EntityBlend(n\OBJ2, BLEND_ADD)
			SpriteViewMode(n\OBJ2, 2)
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-860-2", "Speed") / 100.0
			
			Temp = GetINIFloat(NPCsFile, "SCP-860-2", "Scale") / 20.0
			ScaleEntity(n\OBJ, Temp, Temp, Temp)	
			
			MeshCullBox(n\OBJ, (-MeshWidth(n\OBJ)) * 2.0, (-MeshHeight(n\OBJ)) * 2.0, (-MeshDepth(n\OBJ)) * 2.0, MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 4.0, MeshDepth(n\OBJ) * 4.0)
			
			n\CollRadius = 0.25
			;[End Block]
		Case NPCtype939
			;[Block]
			i = 1
			For n2.NPCs = Each NPCs
				If n\NPCtype = n2\NPCtype And n <> n2 Then i = i + 1
			Next
			n\NVName = "SCP-939-" + i
			
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.3)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype939])
			Temp = GetINIFloat(NPCsFile, "SCP-939", "Scale") / 2.5
			ScaleEntity(n\OBJ, Temp, Temp, Temp)	
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-939", "Speed") / 100.0
			
			n\CollRadius = 0.3
			;[End Block]
		Case NPCtype066
			;[Block]
			n\NVName = "SCP-066"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype066])
			
			Temp = GetINIFloat(NPCsFile, "SCP-066", "Scale") / 2.5
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-066", "Speed") / 100.0
			;[End Block]
		Case NPCtype966
			;[Block]
			i = 1
			For n2.NPCs = Each NPCs
				If n\NPCtype = n2\NPCtype And n <> n2 Then i = i + 1
			Next
			n\NVName = "SCP-966-" + i
			
			n\Collider = CreatePivot()
			EntityRadius n\Collider,0.2
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype966])
			EntityFX(n\OBJ, 1)
			
			Temp = GetINIFloat(NPCsFile, "SCP-966", "Scale") / 40.0
			ScaleEntity(n\OBJ, Temp, Temp, Temp)	
			
			SetAnimTime(n\OBJ, 15.0)
			
			EntityType(n\Collider, HIT_PLAYER)
			
			n\Speed = (GetINIFloat(NPCsFile, "SCP-966", "Speed") / 100.0)
			;[End Block]
		Case NPCtype1499_1
			;[Block]
			n\NVName = "Unidentified"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype1499_1])
			
			Temp = GetINIFloat(NPCsFile, "SCP-1499-1", "Scale") / 4.0 * Rnd(0.8, 1.0)
			
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			EntityFX(n\OBJ, 1)
			EntityAutoFade(n\OBJ, HideDistance * 2.5, HideDistance * 2.95)
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-1499-1", "Speed") / 100.0 * Rnd(0.9, 1.1)
			;[End Block]
		Case NPCtype008_1
			;[Block]
			n\NVName = "Human"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.2)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtype008_1])
			
			Temp = GetINIFloat(NPCsFile, "SCP-008-1", "Scale") / MeshWidth(n\OBJ)
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0)
			
			SetNPCFrame(n, 11.0)
			
			n\Speed = GetINIFloat(NPCsFile, "SCP-008-1", "Speed") / 100.0
			
			n\Sound = LoadSound_Strict("SFX\SCP\008_1\Breath.ogg")
			
			n\HP = 120
			;[End Block]
		Case NPCtypeClerk
			;[Block]
			n\NVName = "Human"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.32)
			EntityType(n\Collider, HIT_PLAYER)
			
			n\OBJ = CopyEntity(o\NPCModelID[NPCtypeClerk])
			
			Temp = GetINIFloat(NPCsFile, "Clerk", "Scale") / MeshWidth(n\OBJ)
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			n\Speed = GetINIFloat(NPCsFile, "Clerk", "Speed") / 100.0
			
			MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0)
			
			n\CollRadius = 0.32
			;[End Block]
		Case NPCtypeVehicle
			;[Block]
			n\NVName = "Vehicle"
			n\Collider = CreatePivot()
			EntityRadius(n\Collider, 0.32)
			EntityType(n\Collider, HIT_PLAYER)
			
            n\OBJ = CopyEntity(o\NPCModelID[NPCtypeVehicle])
			
			Temp = GetINIFloat(NPCsFile, "Vehicle", "Scale") / MeshWidth(n\OBJ)
			ScaleEntity(n\OBJ, Temp, Temp, Temp)
			
			MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0)
			
			n\Speed = GetINIFloat(NPCsFile, "Vehicle", "Speed") / 100.0
			
		    n\CollRadius = 0.72
			;[End Block]
	End Select
	
	PositionEntity(n\Collider, x, y, z, True)
	PositionEntity(n\OBJ, x, y, z, True)
	
	ResetEntity(n\Collider)
	
	n\ID = 0
	n\ID = FindFreeNPCID()
	
	NPCSpeedChange(n)
	
	Return(n)
End Function

Function RemoveNPC(n.NPCs)
	If n = Null Then Return
	
	If n\OBJ2 <> 0 Then FreeEntity(n\OBJ2) : n\OBJ2 = 0
	If n\OBJ3 <> 0 Then FreeEntity(n\OBJ3) : n\OBJ3 = 0
	If n\OBJ4 <> 0 Then FreeEntity(n\OBJ4) : n\OBJ4 = 0
	
	If (Not n\SoundCHN_IsStream)
		If n\SoundCHN <> 0 And ChannelPlaying(n\SoundCHN) Then
			StopChannel(n\SoundCHN)
		EndIf
	Else
		If n\SoundCHN <> 0 Then StopStream_Strict(n\SoundCHN)
	EndIf
	
	If (Not n\SoundCHN2_IsStream) Then
		If (n\SoundCHN2 <> 0 And ChannelPlaying(n\SoundCHN2)) Then StopChannel(n\SoundCHN2)
	Else
		If n\SoundCHN2 <> 0 Then StopStream_Strict(n\SoundCHN2)
	EndIf
	
	If n\Collider <> 0 Then FreeEntity(n\Collider) : n\Collider = 0	
	If n\OBJ <> 0 Then FreeEntity(n\OBJ) : n\OBJ = 0
	If n\Sound <> 0 Then FreeSound_Strict(n\Sound) : n\Sound = 0
	If n\Sound2 <> 0 Then FreeSound_Strict(n\Sound2) : n\Sound2 = 0
	
	Delete(n)
End Function

Function UpdateNPCs()
	CatchErrors("Uncaught (UpdateNPCs)")
	
	Local n.NPCs, n2.NPCs, d.Doors, de.Decals, r.Rooms, e.Events, w.Waypoints, p.Particles, wp.WayPoints
	Local i%, j%, Dist#, Dist2#, Angle#, x#, x2#, y#, z#, z2#, PrevFrame#, PlayerSeeAble%, RN$
	Local Target%, Pvt%, Pick%, GroupDesignation$
	
	For n.NPCs = Each NPCs
		; ~ A variable to determine if the NPC is in the facility or not
		n\InFacility = CheckForNPCInFacility(n)
		
		Select n\NPCtype
			Case NPCtype173
				;[Block]
				If Curr173\Idle <> 3 And (PlayerRoom\RoomTemplate\Name <> "gateb" And EntityY(me\Collider) =< 1040.0 * RoomScale) Then
					Dist = EntityDistance(n\Collider, me\Collider)		
					
					n\State3 = 1.0
					
					If n\Idle < 2 Then
						If n\IdleTimer > 0.1
							n\Idle = 1
							n\IdleTimer = Max(n\IdleTimer - fps\FPSFactor[0], 0.1)
						ElseIf n\IdleTimer = 0.1
							n\Idle = 0
							n\IdleTimer = 0.0
						EndIf
						
						PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.32, EntityZ(n\Collider))
						RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider) - 180.0, 0.0)
						
						If n\Idle = 0 Then
							Local Temp% = False
							Local Move% = True
							
							If Dist < 15.0 Then
								If Dist < 10.0 Then 
									If EntityVisible(n\Collider, me\Collider) Then
										Temp = True
										n\EnemyX = EntityX(me\Collider, True)
										n\EnemyY = EntityY(me\Collider, True)
										n\EnemyZ = EntityZ(me\Collider, True)
									EndIf
								EndIf										
								
								Local SoundVol# = Max(Min((Distance(EntityX(n\Collider), n\PrevX, EntityZ(n\Collider), n\PrevZ) * 2.5), 1.0), 0.0)
								
								n\SoundCHN = LoopSound2(StoneDragSFX, n\SoundCHN, Camera, n\Collider, 10.0, n\State)
								
								n\PrevX = EntityX(n\Collider)
								n\PrevZ = EntityZ(n\Collider)				
								
								If (me\BlinkTimer < -16.0 Lor me\BlinkTimer > -6.0) And (Not wi\IsNVGBlinking) And me\LightBlink =< 0.0 Then
									If EntityInView(n\OBJ, Camera) Then Move = False
								EndIf
							EndIf
							
							If chs\NoTarget Then Move = True
							
							; ~ Doesn't move
							If (Not Move) Then
								me\BlurVolume = Max(Max(Min((4.0 - Dist) / 6.0, 0.9), 0.1), me\BlurVolume)
								me\CurrCameraZoom = Max(me\CurrCameraZoom, (Sin(Float(MilliSecs()) / 20.0) + 1.0) * 15.0 * Max((3.5 - Dist) / 3.5, 0.0))								
								
								If Dist < 3.5 And MilliSecs() - n\LastSeen > 60000 And Temp Then
									PlaySound_Strict(HorrorSFX[Rand(3, 4)])
									
									n\LastSeen = MilliSecs()
								EndIf
								
								If Dist < 1.5 And Rand(700) = 1 Then PlaySound2(Scp173SFX[Rand(0, 2)], Camera, n\OBJ)
								
								If Dist < 1.5 And n\LastDist > 2.0 And Temp Then
									me\CurrCameraZoom = 40.0
									me\HeartBeatRate = Max(me\HeartBeatRate, 140.0)
									me\HeartBeatVolume = 0.5
									
									Select Rand(5)
										Case 1
											;[Block]
											PlaySound_Strict(HorrorSFX[1])
											;[End Block]
										Case 2
											;[Block]
											PlaySound_Strict(HorrorSFX[2])
											;[End Block]
										Case 3
											;[Block]
											PlaySound_Strict(HorrorSFX[9])
											;[End Block]
										Case 4
											;[Block]
											PlaySound_Strict(HorrorSFX[10])
											;[End Block]
										Case 5
											;[Block]
											PlaySound_Strict(HorrorSFX[14])
											;[End Block]
									End Select
								EndIf									
								
								n\LastDist = Dist
								
								n\State = Max(0.0, n\State - fps\FPSFactor[0] / 20.0)
							Else 
								; ~ Teleport to a room closer to the player
								If Dist > 50.0 Then
									If Rand(70) = 1 Then
										If (PlayerRoom\RoomTemplate\Name <> "gateb" And EntityY(me\Collider) =< 1040.0 * RoomScale) And PlayerRoom\RoomTemplate\Name <> "gatea" And PlayerRoom\RoomTemplate\Name <> "pocketdimension" Then
											For w.Waypoints = Each WayPoints
												If w\door = Null And Rand(5) = 1 Then
													x = Abs(EntityX(me\Collider) - EntityX(w\OBJ, True))
													If x < 25.0 And x > 15.0 Then
														z = Abs(EntityZ(me\Collider) - EntityZ(w\OBJ, True))
														If z < 25.0 And z > 15.0 Then
															PositionEntity(n\Collider, EntityX(w\OBJ, True), EntityY(w\OBJ, True) + 0.25, EntityZ(w\OBJ, True))
															ResetEntity(n\Collider)
															Exit
														EndIf
													EndIf
												EndIf
											Next
										EndIf
									EndIf
								ElseIf Dist > HideDistance * 0.8 ; ~ Move randomly from waypoint to another
									If Rand(70) = 1 Then TeleportCloser(n)
								Else ; ~ Actively move towards the player
									n\State = CurveValue(SoundVol, n\State, 3.0)
									
									; ~ Tries to open doors
									If Rand(20) = 1 Then
										For d.Doors = Each Doors
											If d\Locked = 0 And (Not d\Open) And d\Code = "" And d\KeyCard = 0 Then
												For i = 0 To 1
													If d\Buttons[i] <> 0 Then
														If Abs(EntityX(n\Collider) - EntityX(d\Buttons[i])) < 0.5 Then
															If Abs(EntityZ(n\Collider) - EntityZ(d\Buttons[i])) < 0.5 Then
																If (d\OpenState >= 180.0 Lor d\OpenState =< 0.0) Then
																	Pvt = CreatePivot()
																	PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider) + 0.5, EntityZ(n\Collider))
																	PointEntity(Pvt, d\Buttons[i])
																	MoveEntity(Pvt, 0.0, 0.0, n\Speed * 0.6)
																	
																	If EntityPick(Pvt, 0.5) = d\Buttons[i] Then 
																		PlaySound_Strict(LoadTempSound("SFX\Door\DoorOpen173.ogg"))
																		UseDoor(d, False)
																	EndIf
																	FreeEntity(Pvt)
																EndIf
															EndIf
														EndIf
													EndIf
												Next
											EndIf
										Next
									EndIf
									
									If chs\NoTarget Then
										Temp = False
										n\EnemyX = 0.0
										n\EnemyY = 0.0
										n\EnemyZ = 0.0
									EndIf
									
									; ~ Attacks
									If Temp Then 				
										If Dist < 0.65 Then
											If me\KillTimer >= 0.0 And (Not chs\GodMode) Then
												Select PlayerRoom\RoomTemplate\Name
													Case "room2clockroom", "room2closets", "room895"
														;[Block]
														msg\DeathMsg = SubjectName + ". Cause of death: Fatal cervical fracture. The surveillance tapes confirm that the subject was killed by SCP-173."	
														;[End Block]
													Case "room173intro"
														;[Block]
														msg\DeathMsg = SubjectName + ". Cause of death: Fatal cervical fracture. According to Security Chief Franklin who was present at SCP-173's containment "
														msg\DeathMsg = msg\DeathMsg + "chamber during the breach, the subject was killed by SCP-173 as soon as the disruptions in the electrical network started."
														;[End block]
													Case "room2doors"
														;[Block]
														msg\DeathMsg = Chr(34) + "If I'm not mistaken, one of the main purposes of these rooms was to stop SCP-173 from moving further in the event of a containment breach. "
														msg\DeathMsg = msg\DeathMsg + "So, who's brilliant idea was it to put A GODDAMN MAN-SIZED VENTILATION DUCT in there?" + Chr(34)
														;[End Block]
													Default 
														;[Block]
														msg\DeathMsg = SubjectName + ". Cause of death: Fatal cervical fracture. Assumed to be attacked by SCP-173."
														;[End Block]
												End Select
												
												If (Not chs\GodMode) Then n\Idle = 1
												
												PlaySound_Strict(NeckSnapSFX[Rand(0, 2)])
												If Rand(2) = 1 Then 
													TurnEntity(Camera, 0.0, Rnd(80.0, 100.0), 0.0)
												Else
													TurnEntity(Camera, 0.0, Rnd(-100.0, -80.0), 0.0)
												EndIf
												Kill()
											EndIf
										Else
											PointEntity(n\Collider, me\Collider)
											RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), EntityRoll(n\Collider))
											TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider) + 90.0) * n\Speed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider) + 90.0) * n\Speed * fps\FPSFactor[0])
										EndIf
									Else ; ~ Move to the location where he was last seen							
										If n\EnemyX <> 0.0 Then						
											If DistanceSquared(EntityX(n\Collider), n\EnemyX, EntityZ(n\Collider), n\EnemyZ) > 0.25 Then
												AlignToVector(n\Collider, n\EnemyX - EntityX(n\Collider), 0.0, n\EnemyZ - EntityZ(n\Collider), 3)
												MoveEntity(n\Collider, 0.0, 0.0, n\Speed * fps\FPSFactor[0])
												If Rand(500) = 1 Then n\EnemyX = 0.0 : n\EnemyY = 0.0 : n\EnemyZ = 0.0
											Else
												n\EnemyX = 0.0 : n\EnemyY = 0.0 : n\EnemyZ = 0.0
											EndIf
										Else
											If Rand(400) = 1 Then RotateEntity(n\Collider, 0.0, Rnd(360.0), 10.0)
											TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider) + 90.0) * n\Speed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider) + 90.0) * n\Speed * fps\FPSFactor[0])
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
						PositionEntity(n\Collider, EntityX(n\Collider), Min(EntityY(n\Collider), 0.35), EntityZ(n\Collider))
					Else ; ~ SCP-173 was captured by MTF
						If n\Target <> Null Then
							Local Tmp% = False
							
							If Dist > HideDistance * 0.7 Then
								If (Not EntityVisible(n\OBJ, me\Collider)) Then
									Tmp = True
								EndIf
							EndIf
							If (Not Tmp) Then
								PointEntity(n\OBJ, n\Target\Collider)
								RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0.0, True)								
								Dist = EntityDistance(n\Collider, n\Target\Collider)
								MoveEntity(n\Collider, 0.0, 0.0, 0.016 * fps\FPSFactor[0] * Max(Min((Dist * 2.0 - 1.0) * 0.5, 1.0), -0.5))
								n\GravityMult = 1.0
							Else
								PositionEntity(n\Collider, EntityX(n\Target\Collider), EntityY(n\Target\Collider) + 0.3, EntityZ(n\Target\Collider))
								ResetEntity(n\Collider)
								n\DropSpeed = 0.0
								n\GravityMult = 0.0
							EndIf
						EndIf
						
						PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) + 0.05 + Sin(MilliSecs() * 0.08) * 0.02, EntityZ(n\Collider))
						RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider) - 180.0, 0.0)
						
						ShowEntity(n\OBJ2)
						
						PositionEntity(n\OBJ2, EntityX(n\Collider), EntityY(n\Collider) - 0.05 + Sin(MilliSecs() * 0.08) * 0.02, EntityZ(n\Collider))
						RotateEntity(n\OBJ2, 0.0, EntityYaw(n\Collider) - 180.0, 0.0)
					EndIf
				EndIf
				;[End Block]
			Case NPCtype106
				;[Block]
				If n\Contained Lor (PlayerRoom\RoomTemplate\Name = "gateb" And EntityY(me\Collider) > 1040.0 * RoomScale) Then
					n\Idle = 1
					HideEntity(n\OBJ)
					HideEntity(n\OBJ2)
					PositionEntity(n\OBJ, 0.0, 500.0, 0.0, True)
				Else
					Dist = EntityDistance(n\Collider, me\Collider)
					
					Local Spawn106% = True
					
					; ~ Checking if SCP-106 is allowed to spawn
					If PlayerRoom\RoomTemplate\Name = "dimension1499" Then Spawn106 = False
					If PlayerRoom\RoomTemplate\Name = "room860" Then
						For e.Events = Each Events
							If e\EventID = e_room860 Then
								If e\EventState = 1.0 Then 
									Spawn106 = False
								EndIf
								Exit
							EndIf
						Next
					EndIf
					If PlayerRoom\RoomTemplate\Name = "room049" And EntityY(me\Collider) =< -2848.0 * RoomScale Then
						Spawn106 = False
					EndIf
					; ~ GateA event has been triggered. Don't make SCP-106 disappear!
					; ~ The reason why this is a seperate for loop is because we need to make sure that room860 would not be able to overwrite the "Spawn106" variable
					If PlayerRoom\RoomTemplate\Name = "gatea" Then
						For e.Events = Each Events
							If e\EventID = e_gatea Then
								If e\EventState <> 0.0 Then
									Spawn106 = True
									If PlayerRoom\RoomTemplate\Name = "dimension1499" Then
										n\Idle = 1
									Else
										n\Idle = 0
									EndIf
								EndIf
								Exit
							EndIf
						Next
					EndIf
					
					If (Not Spawn106) And n\State =< 0.0 Then
						n\State = Rnd(22000.0, 27000.0)
						PositionEntity(n\Collider, 0.0, 500.0, 0.0)
					EndIf
					
					If (Not n\Idle) And Spawn106 Then
						If n\State =< 0.0 Then
							If EntityY(n\Collider) < EntityY(me\Collider) - 20.0 - 0.55 Then
								If Not PlayerRoom\RoomTemplate\DisableDecals Then
									de.Decals = CreateDecal(0, EntityX(me\Collider), 0.01, EntityZ(me\Collider), 90.0, Rnd(360.0), 0.0, 0.05, 0.8)
									de\SizeChange = 0.001
								EndIf
								
								n\PrevY = EntityY(me\Collider)
								
								SetAnimTime(n\OBJ, 110.0)
								
								If PlayerRoom\RoomTemplate\Name <> "room895"
									PositionEntity(n\Collider, EntityX(me\Collider), EntityY(me\Collider) - 15.0, EntityZ(me\Collider))
								EndIf
								
								PlaySound_Strict(DecaySFX[0])
							EndIf
							
							If Rand(500) = 1 Then PlaySound2(OldManSFX[Rand(0, 2)], Camera, n\Collider)
							n\SoundCHN = LoopSound2(OldManSFX[4], n\SoundCHN, Camera, n\Collider, 8.0, 0.8)
							
							If n\State > -10.0 Then
								ShouldPlay = 66
								If n\Frame < 259.0 Then
									PositionEntity(n\Collider, EntityX(n\Collider), n\PrevY - 0.15, EntityZ(n\Collider))
									PointEntity(n\OBJ, me\Collider)
									RotateEntity(n\Collider, 0.0, CurveValue(EntityYaw(n\OBJ), EntityYaw(n\Collider), 100.0), 0.0, True)
									
									AnimateNPC(n, 110.0, 259.0, 0.15, False)
								Else
									n\State = -10.0
								EndIf
							Else
								If PlayerRoom\RoomTemplate\Name <> "gatea" Then ShouldPlay = 10
								
								Local Visible% = False
								
								If Dist < 8.0 Then
									Visible = EntityVisible(n\Collider, me\Collider)
								EndIf
								
								If chs\NoTarget Then Visible = False
								
								If Visible Then
									If PlayerRoom\RoomTemplate\Name <> "gatea" Then n\PathTimer = 0.0
									If EntityInView(n\Collider, Camera) Then
										GiveAchievement(Achv106)
										
										me\BlurVolume = Max(Max(Min((4.0 - Dist) / 6.0, 0.9), 0.1), me\BlurVolume)
										me\CurrCameraZoom = Max(me\CurrCameraZoom, (Sin(Float(MilliSecs()) / 20.0) + 1.0) * 20.0 * Max((4.0 - Dist) / 4.0, 0.0))
										
										If MilliSecs() - n\LastSeen > 60000 Then 
											me\CurrCameraZoom = 40.0
											PlaySound_Strict(HorrorSFX[6])
											n\LastSeen = MilliSecs()
										EndIf
									EndIf
								Else
									n\State = n\State - fps\FPSFactor[0]
								EndIf
								
								If Dist > 0.8 Then
									If (Dist > 25.0 Lor PlayerRoom\RoomTemplate\Name = "pocketdimension" Lor Visible Lor n\PathStatus <> 1) And PlayerRoom\RoomTemplate\Name <> "gatea" Then 
										If (Dist > 40.0 Lor PlayerRoom\RoomTemplate\Name = "pocketdimension") Then
											TranslateEntity(n\Collider, 0.0, ((EntityY(me\Collider) - 0.14) - EntityY(n\Collider)) / 50.0, 0.0)
										EndIf
										
										n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 10.0)
										
										PointEntity(n\OBJ, me\Collider)
										RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0.0)
										
										If me\KillTimer >= 0.0 Then
											PrevFrame = n\Frame
											AnimateNPC(n, 284.0, 333.0, n\CurrSpeed * 43.0)
											
											If PrevFrame =< 286.0 And n\Frame > 286.0 Then
												PlaySound2(StepSFX(2, 0, Rand(0, 2)), Camera, n\Collider, 6.0, Rnd(0.8, 1.0))	
											ElseIf PrevFrame =< 311.0 And n\Frame > 311.0 
												PlaySound2(StepSFX(2, 0, Rand(0, 2)), Camera, n\Collider, 6.0, Rnd(0.8, 1.0))
											EndIf
										Else 
											n\CurrSpeed = 0.0
										EndIf
										
										n\PathTimer = Max(n\PathTimer - fps\FPSFactor[0], 0.0)
										If n\PathTimer =< 0.0 Then
											n\PathStatus = FindPath(n, EntityX(me\Collider, True), EntityY(me\Collider, True), EntityZ(me\Collider, True))
											n\PathTimer = 70.0 * 10.0
										EndIf
									Else 
										If n\PathTimer =< 0.0 Then
											n\PathStatus = FindPath(n, EntityX(me\Collider, True), EntityY(me\Collider, True), EntityZ(me\Collider, True))
											n\PathTimer = 70.0 * 10.0
											n\CurrSpeed = 0.0
										Else
											n\PathTimer = Max(n\PathTimer - fps\FPSFactor[0], 0.0)
											
											If n\PathStatus = 2 Then
												n\CurrSpeed = 0.0
											ElseIf n\PathStatus = 1
												While n\Path[n\PathLocation] = Null
													If n\PathLocation > 19 Then 
														n\PathLocation = 0 : n\PathStatus = 0
														Exit
													Else
														n\PathLocation = n\PathLocation + 1
													EndIf
												Wend
												
												If n\Path[n\PathLocation] <> Null Then 
													TranslateEntity(n\Collider, 0.0, ((EntityY(n\Path[n\PathLocation]\OBJ, True) - 0.11) - EntityY(n\Collider)) / 50.0, 0.0)
													
													PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
													
													Dist2 = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
													
													RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), Min(20.0, Dist2 * 10.0)), 0.0)
													n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 10.0)
													
													PrevFrame = AnimTime(n\OBJ)
													AnimateNPC(n, 284.0, 333.0, n\CurrSpeed * 43.0)
													If PrevFrame =< 286.0 And n\Frame > 286.0 Then
														PlaySound2(StepSFX(2, 0, Rand(0, 2)), Camera, n\Collider, 6.0, Rnd(0.8, 1.0))	
													ElseIf PrevFrame =< 311.0 And n\Frame > 311.0 
														PlaySound2(StepSFX(2, 0, Rand(0, 2)), Camera, n\Collider, 6.0, Rnd(0.8, 1.0))
													EndIf
													
													If Dist2 < 0.2 Then n\PathLocation = n\PathLocation + 1
												EndIf
											ElseIf n\PathStatus = 0
												If n\State3 = 0.0 Then AnimateNPC(n, 334.0, 494.0, 0.3)
												n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 10.0)
											EndIf
										EndIf
									EndIf
								ElseIf PlayerRoom\RoomTemplate\Name <> "gatea" And (Not chs\NoTarget) Then
									If Dist > 0.5 Then 
										n\CurrSpeed = CurveValue(n\Speed * 2.5, n\CurrSpeed, 10.0)
									Else
										n\CurrSpeed = 0.0
									EndIf
									AnimateNPC(n, 105.0, 110.0, 0.15, False)
									
									If me\KillTimer >= 0.0 And me\FallTimer >= 0.0 Then
										PointEntity(n\OBJ, me\Collider)
										RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0.0)									
										
										If Ceil(n\Frame) = 110.0 And (Not chs\GodMode) Then
											PlaySound_Strict(DamageSFX[1])
											PlaySound_Strict(HorrorSFX[5])											
											If PlayerRoom\RoomTemplate\Name = "pocketdimension" Then
												msg\DeathMsg = SubjectName + ". Body partially decomposed by what is assumed to be SCP-106's " + Chr(34) + "corrosion" + Chr(34) + " effect. Body disposed of via incineration."
												Kill(True)
											Else
												PlaySound_Strict(OldManSFX[3])
												me\FallTimer = Min(-1.0, me\FallTimer)
												PositionEntity(me\Head, EntityX(Camera, True), EntityY(Camera, True), EntityZ(Camera, True), True)
												ResetEntity(me\Head)
												RotateEntity(me\Head, 0.0, EntityYaw(Camera) + Rnd(-45.0, 45.0), 0.0)
											EndIf
										EndIf
									EndIf
								EndIf
							EndIf 
							MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
							
							If n\State =< Rnd(-3500.0, -3000.0) Then 
								If (Not EntityInView(n\OBJ, Camera)) And Dist > 5.0 Then
									n\State = Rnd(22000.0, 27000.0)
									PositionEntity(n\Collider, 0.0, 500.0, 0.0)
								EndIf
							EndIf
							
							If me\FallTimer < -250.0 Then
								MoveToPocketDimension()
								n\State = 250.0 ; ~ Make SCP-106 idle for a while
							EndIf
							
							If n\Reload = 0.0 Then
                                If Dist > 10.0 And (PlayerRoom\RoomTemplate\Name <> "gateb" And EntityY(me\Collider) =< 1040.0 * RoomScale) And PlayerRoom\RoomTemplate\Name <> "pocketdimension" And PlayerRoom\RoomTemplate\Name <> "gatea" And n\State < -5.0 Then ; ~ Timer idea -- Juanjpro
                                    If (Not EntityInView(n\OBJ, Camera)) Then
                                        TurnEntity(me\Collider, 0.0, 180.0, 0.0)
                                        Pick = EntityPick(me\Collider, 5.0)
                                        TurnEntity(me\Collider, 0.0, 180.0, 0.0)
                                        If Pick <> 0 Then
											TeleportEntity(n\Collider, PickedX(), PickedY(), PickedZ(), n\CollRadius)
                                            PointEntity(n\Collider, me\Collider)
                                            RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), 0.0)
                                            MoveEntity(n\Collider, 0.0, 0.0, -2.0)
                                            PlaySound2(OldManSFX[3], Camera, n\Collider)
											n\SoundCHN2 = PlaySound2(OldManSFX[6 + Rand(0, 2)], Camera, n\Collider)
                                            n\PathTimer = 0.0
                                            n\Reload = (70.0 * 10.0) / (SelectedDifficulty\OtherFactors + 1.0)
										EndIf
                                    EndIf
                                EndIf
                            EndIf
                            n\Reload = Max(0.0, n\Reload - fps\FPSFactor[0])
							
							UpdateSoundOrigin(n\SoundCHN2, Camera, n\Collider)
						Else ; ~ Idling outside the map
							n\CurrSpeed = 0.0
							MoveEntity(n\Collider, 0.0, ((EntityY(me\Collider) - 30.0) - EntityY(n\Collider)) / 200.0, 0.0)
							n\DropSpeed = 0.0
							SetNPCFrame(n, 110.0)
							
							If (Not PlayerRoom\RoomTemplate\DisableDecals) Then
								If PlayerRoom\RoomTemplate\Name <> "gatea" Then
									If SelectedDifficulty\AggressiveNPCs Then
										n\State = n\State - (fps\FPSFactor[0] * 2.0)
									Else
										n\State = n\State - fps\FPSFactor[0]
									EndIf
								EndIf
							EndIf
						EndIf
						
						ResetEntity(n\Collider)
						n\DropSpeed = 0.0
						PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.15, EntityZ(n\Collider))
						
						RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider), 0.0)
						
						PositionEntity(n\OBJ2, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
						RotateEntity(n\OBJ2, 0.0, EntityYaw(n\Collider) - 180.0, 0.0)
						MoveEntity(n\OBJ2, 0.0, 8.6 * 0.11, -1.5 * 0.11)
						
						If PlayerRoom\RoomTemplate\Name = "pocketdimension" Lor PlayerRoom\RoomTemplate\Name = "gatea" Lor (PlayerRoom\RoomTemplate\Name = "gateb" And EntityY(me\Collider) > 1040.0 * RoomScale) Then
							HideEntity(n\OBJ2)
						Else
							If Dist < opt\CameraFogFar * LightVolume * 0.6 Then
								HideEntity(n\OBJ2)
							Else
								If n\State =< -10.0 Then
									ShowEntity(n\OBJ2)
									EntityAlpha(n\OBJ2, Min(Dist - opt\CameraFogFar * LightVolume * 0.6, 1.0))
								EndIf
							EndIf
						EndIf						
					Else
						HideEntity(n\OBJ2)
					EndIf
				EndIf
				;[End Block]
			Case NPCtype096
				;[Block]
				Dist = EntityDistance(me\Collider, n\Collider)
				Angle = WrapAngle(DeltaYaw(n\Collider, me\Collider))
				
				If wi\SCRAMBLE > 0 And Dist < 16.0 And (Angle < 135.0 Lor Angle > 225.0) And EntityVisible(Camera, n\OBJ2) Then
					ShowEntity(n\OBJ2)
					ScaleSprite(n\OBJ2, Rnd(0.06, 0.08), Rnd(0.07, 0.09))
					PositionEntity(n\OBJ2, Rnd(0.1) - 0.05, Rnd(0.1) - 0.05, Rnd(0.1) - 0.05)
				Else
					HideEntity(n\OBJ2)
				EndIf
				
				Select n\State
					Case 0.0
						;[Block]
						If Dist < 8.0 Then
							GiveAchievement(Achv096)
							If (Not n\SoundCHN) Then
								n\SoundCHN = StreamSound_Strict("SFX\Music\096.ogg", 0)
								n\SoundCHN_IsStream = True
							Else
								UpdateStreamSoundOrigin(n\SoundCHN, Camera, n\Collider, 8.0, 1.0)
							EndIf
							
							If n\State3 = -1.0
								AnimateNPC(n, 936.0, 1263.0, 0.1, False)
								If n\Frame >= 1262.9
									n\State = 5.0
									n\State3 = 0.0
									SetNPCFrame(n, 312.0)
								EndIf
							Else
								AnimateNPC(n, 936.0, 1263.0, 0.1)
								If n\State3 < 70.0 * 6.0
									n\State3 = n\State3 + fps\FPSFactor[0]
								Else
									If Rand(1, 5) = 1
										n\State3 = -1.0
									Else
										n\State3 = 70.0 * Rand(0, 3)
									EndIf
								EndIf
							EndIf
							
							If (Not chs\NoTarget) Then
								If wi\SCRAMBLE = 0 And (Angle < 135.0 Lor Angle > 225.0) And (EntityVisible(Camera, n\OBJ2) And EntityInView(n\OBJ2, Camera)) Then
									If (me\BlinkTimer < -16.0 Lor me\BlinkTimer > -6.0) And me\LightBlink =< 0.0
										PlaySound_Strict(LoadTempSound("SFX\SCP\096\Triggered.ogg"))
										
										me\CurrCameraZoom = 10.0
										
										SetNPCFrame(n, 194.0)
										
										StopStream_Strict(n\SoundCHN) : n\SoundCHN = 0
										n\Sound = 0
										n\State = 1.0
										n\State3 = 0.0
									EndIf
								EndIf
							EndIf
						EndIf
						;[End Block]
					Case 4.0
						;[Block]
						me\CurrCameraZoom = CurveValue(Max(me\CurrCameraZoom, (Sin(Float(MilliSecs()) / 20.0) + 1.0) * 10.0), me\CurrCameraZoom, 8.0)
						
						If n\Target = Null Then 
							If (Not n\SoundCHN) Then
								n\SoundCHN = StreamSound_Strict("SFX\SCP\096\Scream.ogg", 0)
								n\SoundCHN_IsStream = True
							Else
								UpdateStreamSoundOrigin(n\SoundCHN, Camera, n\Collider, 7.5, 1.0)
							EndIf
							
							If (Not n\SoundCHN2) Then
								n\SoundCHN2 = StreamSound_Strict("SFX\Music\096Chase.ogg", 0)
								n\SoundCHN2_IsStream = 2
							Else
								SetStreamVolume_Strict(n\SoundCHN2, Min(Max(8.0 - Dist, 0.6), 1.0) * opt\SFXVolume)
							EndIf
						EndIf
						
						If chs\NoTarget And n\Target = Null Then n\State = 5.0
						
						If me\KillTimer >= 0.0 Then
							If MilliSecs() > n\State3 Then
								n\LastSeen = 0
								If n\Target = Null Then
									If EntityVisible(me\Collider, n\Collider) Then n\LastSeen = 1
								Else
									If EntityVisible(n\Target\Collider, n\Collider) Then n\LastSeen = 1
								EndIf
								n\State3 = MilliSecs() + 3000.0
							EndIf
							
							If n\LastSeen = 1 Then
								n\PathTimer = Max(70.0 * 3.0, n\PathTimer)
								n\PathStatus = 0
								
								If n\Target <> Null Then Dist = EntityDistance(n\Target\Collider, n\Collider)
								
								If Dist < 2.8 Lor n\Frame < 150.0 Then 
									If n\Frame > 193.0 Then n\Frame = 2.0 ; ~ Go to the start of the jump animation
									
									AnimateNPC(n, 2.0, 193.0, 0.7)
									
									If Dist > 1.0 Then 
										n\CurrSpeed = CurveValue(n\Speed * 2.0, n\CurrSpeed, 15.0)
									Else
										n\CurrSpeed = 0.0
										
										If n\Target = Null Then
											If (Not chs\GodMode) Then 
												PlaySound_Strict(DamageSFX[4])
												
												Pvt = CreatePivot()
												me\CameraShake = 30.0
												me\BlurTimer = 2000.0
												msg\DeathMsg = "A large amount of blood found in [DATA REDACTED]. DNA indentified as " + SubjectName + ". Most likely [DATA REDACTED] by SCP-096."
												Kill(True)
												me\KillAnim = 1
												For i = 0 To 6
													PositionEntity(Pvt, EntityX(me\Collider) + Rnd(-0.1, 0.1), EntityY(me\Collider) - 0.05, EntityZ(me\Collider) + Rnd(-0.1, 0.1))
													TurnEntity(Pvt, 90.0, 0.0, 0.0)
													EntityPick(Pvt, 0.3)
													
													de.Decals = CreateDecal(Rand(15, 16), PickedX(), PickedY() + 0.005, PickedZ(), 90.0, Rand(360.0), 0.0, Rnd(0.2, 0.6))
												Next
												FreeEntity(Pvt)
											EndIf
										EndIf				
									EndIf
									
									If n\Target = Null Then
										PointEntity(n\Collider, me\Collider)
									Else
										PointEntity(n\Collider, n\Target\Collider)
									EndIf
								Else
									If n\Target = Null Then 
										PointEntity(n\OBJ, me\Collider)
									Else
										PointEntity(n\OBJ, n\Target\Collider)
									EndIf
									
									RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 5.0), 0.0)
									
									If n\Frame > 847.0 Then n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
									
									If n\Frame < 906 Then
										AnimateNPC(n, 737.0, 906.0, n\Speed * 8.0, False)
									Else
										AnimateNPC(n, 907.0, 935.0, n\CurrSpeed * 8.0)
									EndIf
								EndIf
								
								RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), 0.0, True)
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
							Else
								If n\PathStatus = 1 Then
									If n\Path[n\PathLocation] = Null Then 
										If n\PathLocation > 19 Then 
											n\PathLocation = 0 : n\PathStatus = 0
										Else
											n\PathLocation = n\PathLocation + 1
										EndIf
									Else
										PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
										
										RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 5.0), 0.0)
										
										If n\Frame > 847.0 Then n\CurrSpeed = CurveValue(n\Speed * 1.5, n\CurrSpeed, 15.0)
										MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
										
										If n\Frame < 906.0 Then
											AnimateNPC(n, 737.0, 906.0, n\Speed * 8.0, False)
										Else
											AnimateNPC(n, 907.0, 935.0, n\CurrSpeed * 8.0)
										EndIf
										
										Dist2 = EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ)
										If Dist2 < 0.64 Then
											If n\Path[n\PathLocation]\door <> Null Then
												If (Not n\Path[n\PathLocation]\door\Open) Then
													n\Path[n\PathLocation]\door\Open = True
													n\Path[n\PathLocation]\door\FastOpen = 1
													PlaySound2(OpenDoorFastSFX, Camera, n\Path[n\PathLocation]\door\OBJ)
												EndIf
											EndIf							
											If Dist2 < 0.49 Then n\PathLocation = n\PathLocation + 1
										EndIf 
									EndIf
								Else
									AnimateNPC(n, 1471.0, 1556.0, 0.1)
									
									n\PathTimer = Max(0.0, n\PathTimer - fps\FPSFactor[0])
									If n\PathTimer =< 0 Then
										If n\Target <> Null Then
											n\PathStatus = FindPath(n, EntityX(n\Target\Collider), EntityY(n\Target\Collider) + 0.2, EntityZ(n\Target\Collider))	
										Else
											n\PathStatus = FindPath(n, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))	
										EndIf
										n\PathTimer = 70.0 * 5.0
									EndIf
								EndIf
							EndIf
							
							If Dist > 32.0 Lor EntityY(n\Collider) < -50.0 Then
								If Rand(50) = 1 Then TeleportCloser(n)
							EndIf
						Else
							AnimateNPC(n, Min(27.0, AnimTime(n\OBJ)), 193.0, 0.5)
						EndIf
						;[End Block]
					Case 1.0, 2.0, 3.0
						;[Block]
						If (Not n\SoundCHN) Then
							n\SoundCHN = StreamSound_Strict("SFX\Music\096Angered.ogg", 0)
							n\SoundCHN_IsStream = True
						Else
							UpdateStreamSoundOrigin(n\SoundCHN, Camera, n\Collider, 10.0, 1.0)
						EndIf
						
						If n\State = 1.0 Then ; ~ Get up
							If n\Frame < 312.0 Then
								AnimateNPC(n, 193.0, 311.0, 0.3, False)
								If n\Frame > 310.9 Then n\State = 2.0 : SetNPCFrame(n, 737.0)
							ElseIf n\Frame >= 312.0 And n\Frame =< 422.0
								AnimateNPC(n, 312.0, 422.0, 0.3, False)
								If n\Frame > 421.9 Then SetNPCFrame(n, 677.0)
							Else
								AnimateNPC(n, 677.0, 736.0, 0.3, False)
								If n\Frame > 735.9 Then n\State = 2.0 : SetNPCFrame(n, 737.0)
							EndIf
						ElseIf n\State = 2.0
							AnimateNPC(n, 677.0, 737.0, 0.3, False)
							If n\Frame >= 737.0 Then n\State = 3.0 : n\State2 = 0.0
						ElseIf n\State = 3.0
							n\State2 = n\State2 + fps\FPSFactor[0]
							If n\State2 > 70.0 * 26.0 Then
								AnimateNPC(n, 823.0, 847.0, n\Speed * 8.0, False)
								If n\Frame > 846.9 Then
									n\State = 4.0
									StopStream_Strict(n\SoundCHN) : n\SoundCHN = 0
								EndIf
							Else
								AnimateNPC(n, 1471.0, 1556.0, 0.4)
							EndIf
						EndIf
						;[End Block]
					Case 5.0
						;[Block]
						If Dist < 16.0 Then 
							If Dist < 4.0 Then
								GiveAchievement(Achv096)
							EndIf
							
							If (Not n\SoundCHN) Then
								n\SoundCHN = StreamSound_Strict("SFX\Music\096.ogg", 0)
								n\SoundCHN_IsStream = True
							Else
								UpdateStreamSoundOrigin(n\SoundCHN, Camera, n\Collider, 14.0, 1.0)
							EndIf
							
							If n\Frame >= 422.0 Then
								n\State2 = n\State2 + fps\FPSFactor[0]
								If n\State2 > 1000.0 Then
									If n\State2 > 1600.0 Then n\State2 = Rnd(0.0, 500.0)
									
									If n\Frame < 1382.0 Then
										n\CurrSpeed = CurveValue(n\Speed * 0.1, n\CurrSpeed, 5.0)
										AnimateNPC(n, 1369.0, 1382.0, n\CurrSpeed * 45.0, False)
									Else
										n\CurrSpeed = CurveValue(n\Speed * 0.1, n\CurrSpeed, 5.0)
										AnimateNPC(n, 1383.0, 1456.0, n\CurrSpeed * 45.0)
									EndIf
									
									If MilliSecs() > n\State3 Then
										n\LastSeen = 0
										If EntityVisible(me\Collider, n\Collider) Then 
											n\LastSeen = 1
										Else
											HideEntity(n\Collider)
											EntityPick(n\Collider, 1.5)
											If PickedEntity() <> 0 Then
												n\Angle = EntityYaw(n\Collider) + Rnd(80.0, 110.0)
											EndIf
											ShowEntity(n\Collider)
										EndIf
										n\State3 = MilliSecs() + 3000.0
									EndIf
									
									If n\LastSeen Then 
										PointEntity(n\OBJ, me\Collider)
										RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 130.0), 0.0)
										If Dist < 1.5 Then n\State2 = 0.0
									Else
										RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle, EntityYaw(n\Collider), 50.0), 0.0)
									EndIf
								Else
									If n\Frame > 472.0 Then ; ~ Walk to idle
										n\CurrSpeed = CurveValue(n\Speed * 0.05, n\CurrSpeed, 8.0)
										AnimateNPC(n, 1383.0, 1469.0, n\CurrSpeed * 45.0, False)
										If n\Frame >= 1468.9 Then SetNPCFrame(n, 423.0)
									Else
										n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 4.0)	
										AnimateNPC(n, 423.0, 471.0, 0.2)
									EndIf
								EndIf
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
							Else
								AnimateNPC(n, 312.0, 422.0, 0.3, False)
							EndIf
							
							If (Not chs\NoTarget) Then
								If wi\SCRAMBLE = 0 And (Angle < 135.0 Lor Angle > 225.0) And (EntityVisible(Camera, n\OBJ2) And EntityInView(n\OBJ2, Camera)) Then
									If (me\BlinkTimer < -16.0 Lor me\BlinkTimer > -6.0) And me\LightBlink =< 0.0
										PlaySound_Strict(LoadTempSound("SFX\SCP\096\Triggered.ogg"))
										
										me\CurrCameraZoom = 10.0
										
										If n\Frame >= 422.0 Then SetNPCFrame(n, 677.0)
										StopStream_Strict(n\SoundCHN) : n\SoundCHN = 0
										n\Sound = 0
										n\State = 2.0
									EndIf
								EndIf
							EndIf
						EndIf
						;[End Block]
				End Select
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.03, EntityZ(n\Collider))
				
				RotateEntity(n\OBJ, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0)
				;[End Block]
			Case NPCtype049
				;[Block]
				; ~ n\State: The "main state" of the NPC
				
				; ~ n\State2: Attacks the player when the value is above 0.0
				
				; ~ n\State3: Timer for updating the path again
				
				PrevFrame = n\Frame
				
				Dist = EntityDistance(me\Collider, n\Collider)
				
				n\BlinkTimer = 1.0
				
				If n\Idle > 0.1 Then
					If PlayerRoom\RoomTemplate\Name <> "room049" Then
						n\Idle = Max(n\Idle - (1 + SelectedDifficulty\AggressiveNPCs) * fps\FPSFactor[0], 0.1)
					EndIf
					n\DropSpeed = 0
					If ChannelPlaying(n\SoundCHN) Then StopChannel(n\SoundCHN)
					If ChannelPlaying(n\SoundCHN2) Then StopChannel(n\SoundCHN2)
					PositionEntity(n\Collider, 0.0, -500.0, 0.0)
					PositionEntity(n\OBJ, 0.0, -500.0, 0.0)
				Else
					If n\Idle = 0.1 Then
						If PlayerInReachableRoom() Then
							For i = 0 To 3
								If PlayerRoom\Adjacent[i] <> Null Then
									For j = 0 To 3
										If PlayerRoom\Adjacent[i]\Adjacent[j] <> Null Then
											TeleportEntity(n\Collider, PlayerRoom\Adjacent[i]\Adjacent[j]\x, 0.5, PlayerRoom\Adjacent[i]\Adjacent[j]\z, n\CollRadius, True)
											Exit
										EndIf
									Next
									Exit
								EndIf
							Next
							n\Idle = 0.0
						EndIf
					EndIf
					
					Select n\State
						Case 0.0 ; ~ Nothing (used for events)
							;[Block]
							;[End Block]
						Case 1.0 ; ~ Looking around before getting active
							;[Block]
							If n\Frame >= 538.0 Then
								AnimateNPC(n, 659.0, 538.0, -0.45, False)
								If n\Frame > 537.9 Then SetNPCFrame(n, 37.0)
							Else
								AnimateNPC(n, 37.0, 269.0, 0.7, False)
								If n\Frame > 268.9 Then n\State = 2.0
							EndIf
							;[End Block]
						Case 2.0 ; ~ Being active
							;[Block]
							If (Dist < HideDistance * 2.0) And (Not n\Idle) And PlayerInReachableRoom(True) Then
								n\SoundCHN = LoopSound2(n\Sound, n\SoundCHN, Camera, n\Collider)
								PlayerSeeAble = MeNPCSeesPlayer(n)
								If PlayerSeeAble = True Lor n\State2 > 0.0 And (Not chs\NoTarget) Then ; ~ Attack
									GiveAchievement(Achv049)
									
									; ~ Playing a sound after detecting the player
									If n\PrevState =< 1 And (Not ChannelPlaying(n\SoundCHN2)) Then
										If n\Sound2 <> 0 Then FreeSound_Strict(n\Sound2)
										n\Sound2 = LoadSound_Strict("SFX\SCP\049\Spotted" + Rand(1, 7) + ".ogg")
										n\SoundCHN2 = LoopSound2(n\Sound2, n\SoundCHN2, Camera, n\OBJ)
										n\PrevState = 2
									EndIf
									n\PathStatus = 0
									n\PathTimer = 0.0
									n\PathLocation = 0
									If PlayerSeeAble = True Then n\State2 = 70.0 * 2.0
									
									PointEntity(n\OBJ, me\Collider)
									RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0)
									
									If Dist < 0.5 Then
										If wi\HazmatSuit > 0 Then
											me\BlurTimer = me\BlurTimer + fps\FPSFactor[0] * 2.5
											If me\BlurTimer > 250.0 And me\BlurTimer - fps\FPSFactor[0] * 2.5 =< 250.0 And n\PrevState <> 3 Then
												If n\SoundCHN2 <> 0 Then StopChannel(n\SoundCHN2)
												n\SoundCHN2 = PlaySound_Strict(LoadTempSound("SFX\SCP\049\TakeOffHazmat.ogg"))
												n\PrevState = 3
											ElseIf me\BlurTimer >= 500.0
												For i = 0 To MaxItemAmount - 1
													If Inventory[i] <> Null Then
														If Instr(Inventory[i]\ItemTemplate\TempName, "hazmatsuit") And wi\HazmatSuit < 3 Then
															If Inventory[i]\State2 < 3.0 Then
																Inventory[i]\State2 = Inventory[i]\State2 + 1.0
																me\BlurTimer = 260.0
																me\CameraShake = 2.0
															Else
																RemoveItem(Inventory[i])
																wi\HazmatSuit = 0
															EndIf
															Exit
														EndIf
													EndIf
												Next
											EndIf
										ElseIf I_714\Using = 1 Then
											me\BlurTimer = me\BlurTimer + fps\FPSFactor[0] * 2.5
											If me\BlurTimer > 250.0 And me\BlurTimer - fps\FPSFactor[0] * 2.5 =< 250.0 And n\PrevState <> 3 Then
												If n\SoundCHN2 <> 0 Then StopChannel(n\SoundCHN2)
												n\SoundCHN2 = PlaySound_Strict(LoadTempSound("SFX\SCP\049\714Equipped.ogg"))
												n\PrevState = 3
											ElseIf me\BlurTimer >= 500.0
												I_714\Using = 0
											EndIf
										Else
											me\CurrCameraZoom = 20.0
											me\BlurTimer = 500.0
											
											If (Not chs\GodMode) Then
												If PlayerRoom\RoomTemplate\Name = "room049"
													msg\DeathMsg = "Three (3) active instances of SCP-049-2 discovered in the tunnel outside SCP-049's containment chamber. Terminated by Nine-Tailed Fox."
													For e.Events = Each Events
														If e\EventID = e_room049 Then e\EventState = -1.0 : Exit
													Next
												Else
													If Rand(2) = 1 Then
														GroupDesignation = "Nine-Tailed Fox"
													Else
														GroupDesignation = "See No Evil"
													EndIf
													msg\DeathMsg = "An active instance of SCP-049-2 was discovered in [DATA REDACTED]. Terminated by " + GroupDesignation + "."
													Kill() : me\KillAnim = 0
												EndIf
												PlaySound_Strict(HorrorSFX[13])
												If n\Sound2 <> 0 Then FreeSound_Strict(n\Sound2)
												n\Sound2 = LoadSound_Strict("SFX\SCP\049\Kidnap" + Rand(1, 2) + ".ogg")
												n\SoundCHN2 = LoopSound2(n\Sound2, n\SoundCHN2, Camera, n\OBJ)
												n\State = 3.0
											EndIf										
										EndIf
									Else
										n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
										MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])	
										
										If n\PrevState = 3 Then n\PrevState = 2
										
										If Dist < 3.0 Then
											AnimateNPC(n, Max(Min(AnimTime(n\OBJ), 428.0), 387), 463.0, n\CurrSpeed * 38.0)
										Else
											If n\Frame > 428.0 Then
												AnimateNPC(n, Min(AnimTime(n\OBJ), 463.0), 498.0, n\CurrSpeed * 38.0, False)
												If n\Frame > 497.9 Then SetNPCFrame(n, 358.0)
											Else
												AnimateNPC(n, Max(Min(AnimTime(n\OBJ), 358.0), 346.0), 393.0, n\CurrSpeed * 38.0)
											EndIf
										EndIf
									EndIf
								Else ; ~ Finding a path to the player
									If n\PathStatus = 1 ; ~ Path to player found
										While n\Path[n\PathLocation] = Null
											If n\PathLocation > 19 Then 
												n\PathLocation = 0 : n\PathStatus = 0
												Exit
											Else
												n\PathLocation = n\PathLocation + 1
											EndIf
										Wend
										If n\Path[n\PathLocation] <> Null Then
											; ~ Closes doors behind him
											If n\PathLocation > 0 Then
												If n\Path[n\PathLocation - 1] <> Null Then
													If n\Path[n\PathLocation - 1]\door <> Null Then
														If (Not n\Path[n\PathLocation - 1]\door\IsElevatorDoor) Then
															If EntityDistanceSquared(n\Path[n\PathLocation - 1]\OBJ, n\Collider) > 0.09 Then
																If n\Path[n\PathLocation - 1]\door\OpenState = 180.0 And n\Path[n\PathLocation - 1]\door\MTFClose And (n\Path[n\PathLocation - 1]\door\Buttons[0] <> 0 Lor n\Path[n\PathLocation - 1]\door\Buttons[1] <> 0) Then
																	UseDoor(n\Path[n\PathLocation - 1]\door, False)
																EndIf
															EndIf
														EndIf
													EndIf
												EndIf
											EndIf
											
											n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
											PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
											RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0)
											MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
											
											; ~ Opens doors in front of him
											Dist2 = EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ)
											If Dist2 < 0.36 Then
												Temp = True
												If n\Path[n\PathLocation]\door <> Null Then
													If (Not n\Path[n\PathLocation]\door\IsElevatorDoor)
														If (n\Path[n\PathLocation]\door\Locked = 1 Lor n\Path[n\PathLocation]\door\KeyCard <> 0 Lor n\Path[n\PathLocation]\door\Code <> "") And (Not n\Path[n\PathLocation]\door\Open) Then
															Temp = False
														Else
															If (Not n\Path[n\PathLocation]\door\Open) And (n\Path[n\PathLocation]\door\Buttons[0] <> 0 Lor n\Path[n\PathLocation]\door\Buttons[1] <> 0) Then
																UseDoor(n\Path[n\PathLocation]\door, False)
															EndIf
														EndIf
													EndIf
												EndIf
												If Dist2 < 0.04 And Temp Then
													n\PathLocation = n\PathLocation + 1
												ElseIf Dist2 < 0.25 And (Not Temp)
													; ~ Breaking up the path when the door in front of 049 cannot be operated by himself
													n\PathStatus = 0
													n\PathTimer = 0.0
												EndIf
											EndIf
											
											AnimateNPC(n, Max(Min(AnimTime(n\OBJ), 358.0), 346), 393.0, n\CurrSpeed * 38.0)
											
											; ~ Playing a sound if he hears the player
											If n\PrevState = 0 And (Not ChannelPlaying(n\SoundCHN2)) Then
												If n\Sound2 <> 0 Then FreeSound_Strict(n\Sound2)
												If Rand(30) = 1
													n\Sound2 = LoadSound_Strict("SFX\SCP\049\Searching7.ogg")
												Else
													n\Sound2 = LoadSound_Strict("SFX\SCP\049\Searching" + Rand(1, 6) + ".ogg")
												EndIf
												n\SoundCHN2 = LoopSound2(n\Sound2, n\SoundCHN2, Camera, n\OBJ)
												n\PrevState = 1
											EndIf
											
											; ~ Resetting the "PrevState" value randomly, to make SCP-049 talking randomly 
											If Rand(600) = 1 And (Not ChannelPlaying(n\SoundCHN2)) Then n\PrevState = 0
											
											If n\PrevState > 1 Then n\PrevState = 1
										EndIf
									Else ; ~ Stands still and tries to find a path
										n\PathTimer = n\PathTimer + fps\FPSFactor[0]
										If n\PathTimer > 70.0 * (5.0 - (2.0 * SelectedDifficulty\AggressiveNPCs)) Then
											n\PathStatus = FindPath(n, EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
											n\PathTimer = 0.0
											n\State3 = 0.0
											
											; ~ Attempt to find a room (the PlayerRoom or one of it's adjacent rooms) for SCP-049 to go to but select the one closest to him
											If n\PathStatus <> 1 Then
												Local ClosestDist# = EntityDistance(PlayerRoom\OBJ, n\Collider)
												Local ClosestRoom.Rooms = PlayerRoom
												Local CurrDist# = 0.0
												
												For i = 0 To 3
													If PlayerRoom\Adjacent[i] <> Null Then
														CurrDist = EntityDistance(PlayerRoom\Adjacent[i]\OBJ, n\Collider)
														If CurrDist < ClosestDist Then
															ClosestDist = CurrDist
															ClosestRoom = PlayerRoom\Adjacent[i]
														EndIf
													EndIf
												Next
												n\PathStatus = FindPath(n, EntityX(ClosestRoom\OBJ), 0.5, EntityZ(ClosestRoom\OBJ))
											EndIf
											
											; ~ Making 3 attempts at finding a path
											While Int(n\State3) < 3.0
												; ~ Breaking up the path if no "real" path has been found (only 1 waypoint and it is too close)
												If n\PathStatus = 1 Then
													If n\Path[1] <> Null Then
														If n\Path[2] = Null And EntityDistanceSquared(n\Path[1]\OBJ, n\Collider) < 0.16 Then
															n\PathLocation = 0
															n\PathStatus = 0
														EndIf
													EndIf
													If n\Path[0] <> Null And n\Path[1] = Null Then
														n\PathLocation = 0
														n\PathStatus = 0
													EndIf
												EndIf
												
												; ~ No path could still be found, just make SCP-049 go to a room (further away than the very first attempt)
												If n\PathStatus <> 1 Then
													ClosestDist = 100.0 ; ~ Prevent the PlayerRoom to be considered the closest, so SCP-049 wouldn't try to find a path there
													ClosestRoom.Rooms = PlayerRoom
													CurrDist = 0.0
													For i = 0 To 3
														If PlayerRoom\Adjacent[i] <> Null Then
															CurrDist = EntityDistance(PlayerRoom\Adjacent[i]\OBJ, n\Collider)
															If CurrDist < ClosestDist Then
																ClosestDist = CurrDist
																For j = 0 To 3
																	If PlayerRoom\Adjacent[i]\Adjacent[j] <> Null Then
																		If PlayerRoom\Adjacent[i]\Adjacent[j] <> PlayerRoom Then
																			ClosestRoom = PlayerRoom\Adjacent[i]\Adjacent[j]
																			Exit
																		EndIf
																	EndIf
																Next
															EndIf
														EndIf
													Next
													n\PathStatus = FindPath(n, EntityX(ClosestRoom\OBJ), 0.5, EntityZ(ClosestRoom\OBJ))
												EndIf
												
												; ~ Making SCP-049 skip waypoints for doors he can't interact with, but only if the actual path is behind him
												If n\PathStatus = 1 Then
													If n\Path[1] <> Null Then
														If n\Path[1]\door <> Null Then
															If (n\Path[1]\door\Locked = 1 Lor n\Path[1]\door\KeyCard <> 0 Lor n\Path[1]\door\Code <> "") And (Not n\Path[1]\door\Open) Then
																Repeat
																	If n\PathLocation > 19 Then
																		n\PathLocation = 0 : n\PathStatus = 0 : Exit
																	Else
																		n\PathLocation = n\PathLocation + 1
																	EndIf
																	If n\Path[n\PathLocation] <> Null Then
																		If Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation]\OBJ)) > (45.0 - Abs(DeltaYaw(n\Collider, n\Path[1]\OBJ))) Then
																			n\State3 = 3.0
																			Exit
																		EndIf
																	EndIf
																Forever
															Else
																n\State3 = 3.0
															EndIf
														Else
															n\State3 = 3.0
														EndIf
													EndIf
												EndIf
												n\State3 = n\State3 + 1.0
											Wend
										EndIf
										AnimateNPC(n, 269.0, 345.0, 0.2)
									EndIf
								EndIf
								
								If n\CurrSpeed > 0.005 Then
									If (PrevFrame < 361.0 And n\Frame >= 361.0) Lor (PrevFrame < 377.0 And n\Frame >= 377.0) Then
										PlaySound2(StepSFX(5, 0, Rand(4, 6)), Camera, n\Collider, 8.0, Rnd(0.8, 1.0))						
									ElseIf (PrevFrame < 431.0 And n\Frame >= 431.0) Lor (PrevFrame < 447.0 And n\Frame >= 447.0) Then
										PlaySound2(StepSFX(5, 0, Rand(4, 6)), Camera, n\Collider, 8.0, Rnd(0.8, 1.0))
									EndIf
								EndIf
								
								If ChannelPlaying(n\SoundCHN2) = True Then
									UpdateSoundOrigin(n\SoundCHN2, Camera, n\OBJ)
								EndIf
							ElseIf (Not n\Idle)
								If ChannelPlaying(n\SoundCHN) = True Then
									StopChannel(n\SoundCHN)
								EndIf
								If PlayerInReachableRoom(True) And InFacility = 1 Then ; ~ Player is in a room where SCP-049 can teleport to
									If Rand(1, 3 - SelectedDifficulty\OtherFactors) = 1 Then
										TeleportCloser(n)
									Else
										n\Idle = 70.0 * 60.0
									EndIf
								EndIf
							EndIf
							;[End Block]
						Case 3.0 ; ~ The player was killed by SCP-049
							;[Block]
							AnimateNPC(n, 537.0, 660.0, 0.7, False)
							
							PositionEntity(n\Collider, CurveValue(EntityX(me\Collider), EntityX(n\Collider), 20.0), EntityY(n\Collider), CurveValue(EntityZ(me\Collider), EntityZ(n\Collider), 20.0))
							RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(me\Collider) - 180.0, EntityYaw(n\Collider), 40.0), 0.0)
							;[End Block]
						Case 4.0 ; ~ Standing on catwalk
							;[Block]
							If Dist < 8.0 Then
								AnimateNPC(n, 18.0, 19.0, 0.05)
								
								PointEntity(n\OBJ, me\Collider)	
								RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 45.0), 0.0)
								
								n\State3 = 1.0
							ElseIf Dist > HideDistance * 0.8 And n\State3 > 0.0 Then
								n\State = 2.0
								n\State3 = 0.0
								For r.Rooms = Each Rooms
									If EntityDistanceSquared(r\OBJ, n\Collider) < 16.0 Then
										TeleportEntity(n\Collider, EntityX(r\OBJ), 0.1, EntityZ(r\OBJ), n\CollRadius, True)
										Exit
									EndIf
								Next
							EndIf
							;[End Block]
						Case 5.0 ; ~ Going to surveillance room
							;[Block]
							n\SoundCHN = LoopSound2(n\Sound, n\SoundCHN, Camera, n\Collider)
							PlayerSeeAble = MeNPCSeesPlayer(n, True)
							If PlayerSeeAble = True
								n\State = 2.0
								n\PathStatus = 0
								n\PathLocation = 0
								n\PathTimer = 0.0
								n\State3 = 0.0
								n\State2 = 70.0 * 2.0
								n\PrevState = 0
								PlaySound_Strict(LoadTempSound("SFX\Room\Room2SL049Spawn.ogg"))
							ElseIf PlayerSeeAble = 2 And n\State3 > 0.0
								n\PathStatus = FindPath(n, EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
							Else
								If n\State3 = 6.0
									If EntityDistanceSquared(n\Collider, me\Collider) > PowTwo(HideDistance) Then
										n\State = 2.0
										n\PathStatus = 0
										n\PathLocation = 0
										n\PathTimer = 0.0
										n\State3 = 0.0
										n\PrevState = 0
									Else
										If n\PathStatus <> 1 Then n\PathStatus = FindPath(n, EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
									EndIf
								EndIf
								
								If n\PathStatus = 1
									If n\Path[n\PathLocation] = Null
										If n\PathLocation > 19 Then
											n\PathLocation = 0 : n\PathStatus = 0
										Else
											n\PathLocation = n\PathLocation + 1
										EndIf
									Else
										n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
										PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
										RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0.0)
										MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
										
										; ~ Closes doors behind him
										If n\PathLocation > 0 Then
											If n\Path[n\PathLocation - 1] <> Null
												If n\Path[n\PathLocation - 1]\door <> Null Then
													If n\Path[n\PathLocation - 1]\door\KeyCard = 0
														If EntityDistanceSquared(n\Path[n\PathLocation - 1]\OBJ, n\Collider) > 0.09
															If n\Path[n\PathLocation - 1]\door\Open Then UseDoor(n\Path[n\PathLocation - 1]\door, False)
														EndIf
													EndIf
												EndIf
											EndIf
										EndIf
										
										; ~ Opens doors in front of him
										Dist2 = EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ)
										If Dist2 < 0.36 Then
											If n\Path[n\PathLocation]\door <> Null Then
												If (Not n\Path[n\PathLocation]\door\Open) Then UseDoor(n\Path[n\PathLocation]\Door, False)
											EndIf
										EndIf
										
										If Dist2 < 0.04
											n\PathLocation = n\PathLocation + 1
										EndIf
										
										AnimateNPC(n, Max(Min(AnimTime(n\OBJ), 358.0), 346.0), 393.0, n\CurrSpeed * 38.0)
									EndIf
								Else
									Select n\PrevState
										Case 0
											;[Block]
											AnimateNPC(n, 269.0, 345.0, 0.2)
											;[End Block]
										Case 1
											;[Block]
											AnimateNPC(n, 661.0, 891.0, 0.4, False)
											;[End Block]
										Case 2
											;[Block]
											AnimateNPC(n, 892.0, 1119.0, 0.4, False)
											;[End Block]
									End Select
								EndIf
							EndIf
							
							If PlayerRoom\RoomTemplate\Name = "room2sl" Then
								ShouldPlay = 20
							EndIf
							
							If n\CurrSpeed > 0.005 Then
								If (PrevFrame < 361.0 And n\Frame >= 361.0) Lor (PrevFrame < 377.0 And n\Frame >= 377.0) Then
									PlaySound2(StepSFX(5, 0, Rand(4, 6)), Camera, n\Collider, 8.0, Rnd(0.8, 1.0))						
								ElseIf (PrevFrame < 431.0 And n\Frame >= 431.0) Lor (PrevFrame < 447.0 And n\Frame >= 447.0)
									PlaySound2(StepSFX(5, 0, Rand(4, 6)), Camera, n\Collider, 8.0, Rnd(0.8, 1.0))
								EndIf
							EndIf
							
							If ChannelPlaying(n\SoundCHN2) = True Then
								UpdateSoundOrigin(n\SoundCHN2, Camera, n\OBJ)
							EndIf
							;[End Block]
					End Select
				EndIf
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.22, EntityZ(n\Collider))
				
				RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider), 0.0)
				
				n\LastSeen = Max(n\LastSeen - fps\FPSFactor[0], 0.0)
				
				n\State2 = Max(n\State2 - fps\FPSFactor[0], 0.0)
				
				;[End Block]
			Case NPCtype049_2
				;[Block]
				; ~ n\State: Main State
				
				; ~ n\State2: A timer used for the player detection
				
				If (Not n\IsDead) Then
					Dist = EntityDistanceSquared(n\Collider, me\Collider)
					
					PrevFrame = n\Frame
					
					n\BlinkTimer = 1.0
					
					PlayerSeeAble = MeNPCSeesPlayer(n)
					
					Select n\State
						Case 0.0 ; ~ Just lies
							;[Block]
							AnimateNPC(n, 719.0, 777.0, 0.2, False)
							
							If n\Frame = 777.0 Then
								If Rand(700) = 1 Then
									If Dist < 25.0 Then
										SetNPCFrame(n, 719.0)
									EndIf
								EndIf
							EndIf
							;[End Block]
						Case 1.0 ; ~ Stands up
							;[Block]
							If n\Frame >= 682.0 Then 
								AnimateNPC(n, 926.0, 935.0, 0.3, False)
								If n\Frame = 935.0 Then n\State = 2.0
							Else
								AnimateNPC(n, 155.0, 682.0, 1.1, False)
							EndIf
							;[End Block]
						Case 2.0 ; ~ Player is visible, tries to kill
							;[Block]
							If PlayerSeeAble = True Lor n\State2 > 0.0 And (Not chs\Notarget) Then
								If PlayerSeeAble = True
									n\State2 = 70.0 * 2.0
								Else
									n\State2 = Max(n\State2 - fps\FPSFactor[0], 0.0)
								EndIf
								PointEntity(n\OBJ, me\Collider)
								RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
								
								AnimateNPC(n, 936.0, 1017.0, n\CurrSpeed * 60.0)
								n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								
								If Dist < 0.49 Then
									If Abs(DeltaYaw(n\Collider, me\Collider)) =< 60.0 Then
										n\State = 4.0
										If Rand(2) = 1 Then
											SetNPCFrame(n, 2.0)
										Else
											SetNPCFrame(n, 66.0)
										EndIf
									EndIf
								EndIf
								
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\PathLocation = 0
							Else
								n\State = 3.0
							EndIf
							;[End Block]
						Case 3.0 ; ~ Player isn't visible, tries to find
							;[Block]
							If PlayerSeeAble = True And (Not chs\Notarget) Then
								n\State = 2.0
							EndIf
							
							If n\PathStatus = 1 Then
								If n\Path[n\PathLocation] = Null Then 
									If n\PathLocation > 19 Then 
										n\PathLocation = 0 : n\PathStatus = 0
									Else
										n\PathLocation = n\PathLocation + 1
									EndIf
								Else
									PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
									RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
									
									AnimateNPC(n, 936.0, 1017.0, n\CurrSpeed * 60.0)
									n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
									MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
									
									; ~ Opens doors in front of him
									Dist2 = EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ)
									If Dist2 < 0.36 Then
										Temp = True
										If n\Path[n\PathLocation]\door <> Null
											If (Not n\Path[n\PathLocation]\door\IsElevatorDoor)
												If ((n\Path[n\PathLocation]\door\Locked = 1 Lor n\Path[n\PathLocation]\door\KeyCard > 0 Lor n\Path[n\PathLocation]\door\Code <> "") And (Not n\Path[n\PathLocation]\door\Open)) Then
													Temp = False
												Else
													If (Not n\Path[n\PathLocation]\door\Open) Then UseDoor(n\Path[n\PathLocation]\Door, False)
												EndIf
											EndIf
											If n\Path[n\PathLocation]\door\OpenState >= 180.0
												n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
											Else
												n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
											EndIf
										Else
											n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
										EndIf
										If Dist2 < 0.04 And Temp Then
											n\PathLocation = n\PathLocation + 1
										ElseIf Dist2 < 0.25 And (Not Temp)
											n\PathStatus = 0
											n\PathTimer = 0.0
										EndIf
									Else
										n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
									EndIf
								EndIf
							Else
								; ~ No path, stands still
								If n\CurrSpeed =< 0.001 Then
									AnimateNPC(n, 778.0, 926.0, 0.1)
									n\CurrSpeed = 0.0
									
									n\PathTimer = n\PathTimer - fps\FPSFactor[0]
									If n\PathTimer < 70.0 * 5.0
										n\PathTimer = n\PathTimer + Rnd(1.0, 2.0 + (2.0 * SelectedDifficulty\AggressiveNPCs)) * fps\FPSFactor[0]
									Else
										n\EnemyX = 0.0
										n\EnemyY = 0.0
										n\EnemyZ = 0.0
										
										; ~ When stands still, tries to find a room that is close
										For r.Rooms = Each Rooms
											If EntityDistanceSquared(r\OBJ, n\Collider) < 196.0 And EntityDistanceSquared(r\OBJ, n\Collider) > 36.0 Then
												n\EnemyX = EntityX(r\OBJ)
												n\EnemyY = EntityY(r\OBJ)
												n\EnemyZ = EntityZ(r\OBJ)
												Exit
											EndIf
										Next
										
										; ~ The room wasn't found, tries to get the coordinates from a random waypoint
										If n\EnemyX = 0.0 And n\EnemyY = 0.0 And n\EnemyZ = 0.0 Then
											For w.WayPoints = Each WayPoints
												If EntityDistanceSquared(w\OBJ, n\Collider) < 100.0 And EntityDistanceSquared(w\OBJ, n\Collider) > 16.0 Then
													n\EnemyX = EntityX(w\OBJ)
													n\EnemyY = EntityY(w\OBJ)
													n\EnemyZ = EntityZ(w\OBJ)
													Exit
												EndIf
											Next
										EndIf
										
										; ~ Just tries to find a path, if possible
										If n\EnemyX <> 0.0 Lor n\EnemyY <> 0.0 Lor n\EnemyZ <> 0.0
											n\PathStatus = FindPath(n, n\EnemyX, n\EnemyY, n\EnemyZ)
										EndIf
										n\PathTimer = 0.0
									EndIf
								Else
									n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
									AnimateNPC(n, 936.0, 1017.0, n\CurrSpeed * 60.0)
									MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								EndIf
							EndIf
							;[End Block]
						Case 4.0 ; ~ Attacks
							;[Block]
							If n\Frame < 66.0 Then
								AnimateNPC(n, 2.0, 65.0, 0.7, False)
								If n\Frame >= 23.0 And PrevFrame < 23.0 Then
									If Dist < 1.21 Then
										If Abs(DeltaYaw(n\Collider, me\Collider)) =< 60.0
											PlaySound2(DamageSFX[Rand(5, 8)], Camera, n\Collider)
											InjurePlayer(Rnd(0.4, 1.0), 0.0, 0.0, Rnd(0.1, 0.25), 0.2)
											
											If me\Injuries > 3.0 Then
												msg\DeathMsg = SubjectName + ". Cause of death: multiple lacerations and severe blunt force trauma caused by an instance of SCP-049-2."
												Kill(True)
											EndIf
										Else
											PlaySound2(MissSFX, Camera, n\Collider)
										EndIf
									Else
										PlaySound2(MissSFX, Camera, n\Collider, 2.5)
									EndIf
								ElseIf n\Frame >= 64.0
									n\State = 2.0
								EndIf
							Else
								AnimateNPC(n, 66.0, 132.0, 0.7, False)
								If n\Frame >= 90.0 And PrevFrame < 90.0 Then
									If Dist < 1.21 Then
										If Abs(DeltaYaw(n\Collider, me\Collider)) =< 60.0
											PlaySound2(DamageSFX[Rand(5, 8)], Camera, n\Collider)
											InjurePlayer(Rnd(0.4, 1.0), 0.0, 0.0, Rnd(0.1, 0.25), 0.2)
											
											If me\Injuries > 3.0 Then
												msg\DeathMsg = SubjectName + ". Cause of death: multiple lacerations and severe blunt force trauma caused by an instance of SCP-049-2."
												Kill(True)
											EndIf
										Else
											PlaySound2(MissSFX, Camera, n\Collider)
										EndIf
									Else
										PlaySound2(MissSFX, Camera, n\Collider, 2.5)
									EndIf
								ElseIf n\Frame >= 131.0
									n\State = 2.0
								EndIf
							EndIf
							;[End Block]
					End Select
					
					; ~ Loop the walk sound
					If n\CurrSpeed > 0.005 Then
						If (PrevFrame < 970.0 And n\Frame >= 970.0) Lor (PrevFrame < 1012.0 And n\Frame >= 1012.0) Then
							PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.3, 0.5))
						EndIf
					EndIf
					
					; ~ Loop the breath sound
					If n\State > 1.0 Then
						n\SoundCHN = LoopSound2(n\Sound, n\SoundCHN, Camera, n\Collider)
					EndIf
				Else
					; ~ The NPC was killed
					If n\SoundCHN <> 0 Then
						If ChannelPlaying(n\SoundCHN) Then StopChannel(n\SoundCHN)
						If n\Sound <> 0 Then
							FreeSound_Strict(n\Sound) : n\Sound = 0
						EndIf
					EndIf
					AnimateNPC(n, 1035.0, 1072.0, 0.3, False)
				EndIf
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.2, EntityZ(n\Collider))
				RotateEntity(n\OBJ, -90.0, EntityYaw(n\Collider), 0.0)
				;[End Block]
			Case NPCtypeGuard
				;[Block]
				PrevFrame = n\Frame
				
				n\BoneToManipulate = ""
				n\ManipulateBone = False
				n\ManipulationType = 0
				n\NPCNameInSection = "Guard"
				
				Select n\State
					Case 1.0 ; ~ Aims and shoots at the player
						;[Block]
						If n\Frame < 39.0 Lor (n\Frame > 76.0 And n\Frame < 245.0) Lor (n\Frame > 248.0 And n\Frame < 302.0) Lor n\Frame > 344.0 Then
							AnimateNPC(n, 345.0, 357.0, 0.2, False)
							If n\Frame >= 356.0 Then SetNPCFrame(n, 302.0)
						EndIf
						
						If me\KillTimer >= 0.0 Then
							Dist = EntityDistance(n\Collider, me\Collider)
							
							Local ShootAccuracy# = 0.4 + 0.5 * SelectedDifficulty\AggressiveNPCs
							Local DetectDistance# = 11.0
							
							; ~ If at Gate B increase his distance so that he can shoot the player from a distance after they are spotted.
							If PlayerRoom\RoomTemplate\Name = "gateb" Then
								DetectDistance = 21.0
								ShootAccuracy = 0.0
								If Rand(1, 8 - SelectedDifficulty\AggressiveNPCs * 4) < 2 Then ShootAccuracy = 0.03
								
								; ~ Increase accuracy if the player is going slow
								ShootAccuracy = ShootAccuracy + (0.5 - me\CurrSpeed * 20.0)
							EndIf
							
							If Dist < DetectDistance Then
								Pvt = CreatePivot()
								PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider), EntityZ(n\Collider))
								PointEntity(Pvt, me\Collider)
								RotateEntity(Pvt, Min(EntityPitch(Pvt), 20.0), EntityYaw(Pvt), 0.0)
								
								RotateEntity(n\Collider, CurveAngle(EntityPitch(Pvt), EntityPitch(n\Collider), 10.0), CurveAngle(EntityYaw(Pvt), EntityYaw(n\Collider), 10.0), 0.0, True)
								
								PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider) + 0.8, EntityZ(n\Collider))
								PointEntity(Pvt, me\Collider)
								RotateEntity(Pvt, Min(EntityPitch(Pvt), 40.0), EntityYaw(n\Collider), 0.0)
								
								If n\Reload = 0.0
									EntityPick(Pvt, Dist)
									If PickedEntity() = me\Collider Lor n\State3 = 1.0 Then
										Local InstaKillPlayer% = False
										
										If PlayerRoom\RoomTemplate\Name = "room173" Then 
											msg\DeathMsg = SubjectName + ". Cause of death: Gunshot wound to the head. The surveillance tapes confirm that the subject was terminated by Agent Ulgrin shortly after the site lockdown was initiated."
											InstaKillPlayer = True
										ElseIf PlayerRoom\RoomTemplate\Name = "gateb" And EntityY(me\Collider) > 1040.0 * RoomScale
											msg\DeathMsg = Chr(34) + "Agent G. to control. Eliminated a Class D escapee in Gate B's courtyard." + Chr(34)
										Else
											msg\DeathMsg = ""
										EndIf
										
										PlaySound2(GunshotSFX, Camera, n\Collider, 35.0)
										
										RotateEntity(Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0, True)
										PositionEntity(Pvt, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
										MoveEntity(Pvt,0.8 * 0.079, 10.75 * 0.079, 6.9 * 0.079)
										
										PointEntity(Pvt, me\Collider)
										Shoot(EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), ShootAccuracy, True, InstaKillPlayer)
										n\Reload = 7.0
									Else
										n\CurrSpeed = n\Speed
									EndIf
								EndIf
								
								If n\Reload > 0.0 And n\Reload =< 7.0
									AnimateNPC(n, 245, 248, 0.35)
								Else
									If n\Frame < 302.0 Then AnimateNPC(n, 302.0, 344.0, 0.35)
								EndIf
								
								FreeEntity(Pvt)
							Else
								AnimateNPC(n, 302.0, 344.0, 0.35)
							EndIf
							
							n\ManipulateBone = True
							
							If n\State2 = 10.0 Then ; ~ Hacky way of applying spine pitch to specific guards.
								n\BoneToManipulate = "Chest"
								n\ManipulationType = 3
							Else
								n\BoneToManipulate = "Chest"
								n\ManipulationType = 0
							EndIf
						Else
							n\State = 0.0
						EndIf
						;[End Block]
					Case 2.0 ; ~ Shoots
						;[Block]
						AnimateNPC(n, 245.0, 248.0, 0.35)
						If n\Reload = 0.0
							PlaySound2(GunshotSFX, Camera, n\Collider, 15.0)
							p.Particles = CreateParticle(EntityX(n\OBJ, True), EntityY(n\OBJ, True), EntityZ(n\OBJ, True), 1, 0.2, 0.0, 5.0)
							PositionEntity(p\Pvt, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
							RotateEntity(p\Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0, True)
							MoveEntity(p\Pvt, 0.8 * 0.079, 10.75 * 0.079, 6.9 * 0.079)
							n\Reload = 7.0
						EndIf
						;[End Block]
					Case 3.0 ; ~ Follows a path
						;[Block]
						If n\PathStatus = 2 Then
							n\State = 0.0
							n\CurrSpeed = 0.0
						ElseIf n\PathStatus = 1
							If n\Path[n\PathLocation] = Null Then 
								If n\PathLocation > 19 Then 
									n\PathLocation = 0 : n\PathStatus = 0
								Else
									n\PathLocation = n\PathLocation + 1
								EndIf
							Else
								PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
								
								RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
								
								AnimateNPC(n, 1.0, 38.0, n\CurrSpeed * 40.0)
								
								n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
								
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								
								If EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ) < 0.04 Then
									n\PathLocation = n\PathLocation + 1
								EndIf 
							EndIf
						Else
							n\CurrSpeed = 0.0
							n\State = 4.0
						EndIf
						;[End Block]
					Case 4.0
						;[Block]
						AnimateNPC(n, 77.0, 201.0, 0.2)
						
						If Rand(400) = 1 Then n\Angle = Rnd(-180.0, 180.0)
						
						RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle + Sin(MilliSecs() / 50) * 2.0, EntityYaw(n\Collider), 150.0), 0.0, True)
						
						Dist = EntityDistance(n\Collider, me\Collider)
						If Dist < 15.0 Then
							If WrapAngle(EntityYaw(n\Collider) - DeltaYaw(n\Collider, me\Collider)) < 90.0 Then
								If EntityVisible(Pvt, me\Collider) Then n\State = 1.0
							EndIf
						EndIf
						;[End Block]
					Case 5.0 ; ~ Following a target
						;[Block]
						RotateEntity(n\Collider, 0.0, CurveAngle(VectorYaw(n\EnemyX - EntityX(n\Collider), 0.0, n\EnemyZ - EntityZ(n\Collider)) + n\Angle, EntityYaw(n\Collider), 20.0), 0.0)
						
						Dist = Distance(EntityX(n\Collider), n\EnemyX, EntityZ(n\Collider), n\EnemyZ)
						
						AnimateNPC(n, 1.0, 38.0, n\CurrSpeed * 40.0)
						
						If Dist > 2.0 Lor Dist < 1.0  Then
							n\CurrSpeed = CurveValue(n\Speed * Sgn(Dist - 1.5) * 0.75, n\CurrSpeed, 10.0)
						Else
							n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 10.0)
						EndIf
						
						MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
						;[End Block]
					Case 7.0 ; ~ Just walking
						;[Block]
						If n\UseEarphones Then
						    AnimateNPC(n, 623.0, 747.0, 0.2)
						Else
						    AnimateNPC(n, 77.0, 201.0, 0.2)
						EndIf
						;[End Block]
					Case 8.0 ; ~ Idles
						;[Block]
						;[End Block]
					Case 9.0 ; ~ Looks at the player
						;[Block]
						If n\UseEarphones Then
						    AnimateNPC(n, 623.0, 747.0, 0.2)
						Else
						    AnimateNPC(n, 77.0, 201.0, 0.2)
						EndIf
						n\BoneToManipulate = "head"
						n\ManipulateBone = True
						n\ManipulationType = 0
						n\Angle = EntityYaw(n\Collider)
						;[End Block]
					Case 10.0
						;[Block]
						AnimateNPC(n, 1.0, 38.0, n\CurrSpeed * 40.0)
						
						n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
						
						MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
						;[End Block]
					Case 11.0 ; ~ Trying to find the player and kill
						;[Block]
						If n\UseEarphones Then
							If n\Frame < 787.0 Lor (n\Frame > 824.0 And n\Frame < 867.0) Lor (n\Frame > 870.0 And n\Frame < 884.0) Lor n\Frame > 939.0
							    AnimateNPC(n, 927.0, 939.0, 0.2, False)
							    If n\Frame >= 883.0 Then SetNPCFrame(n, 884.0)
						    EndIf
						Else
						    If n\Frame < 39.0 Lor (n\Frame > 76.0 And n\Frame < 245.0) Lor (n\Frame > 248.0 And n\Frame < 302.0) Lor n\Frame > 344.0
							    AnimateNPC(n, 345.0, 357.0, 0.2, False)
							    If n\Frame >= 356.0 Then SetNPCFrame(n, 302.0)
						    EndIf
						EndIf
						
						If me\KillTimer >= 0.0 Then
							Dist = EntityDistance(n\Collider, me\Collider)
							
							Local SearchPlayer% = False
							
							If Dist < 11.0 Then
								If EntityVisible(n\Collider, me\Collider)
									SearchPlayer = True
								EndIf
							EndIf
							
							If SearchPlayer
								Pvt = CreatePivot()
								PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider), EntityZ(n\Collider))
								PointEntity(Pvt, me\Collider)
								RotateEntity(Pvt, Min(EntityPitch(Pvt), 20.0), EntityYaw(Pvt), 0.0)
								
								RotateEntity(n\Collider, CurveAngle(EntityPitch(Pvt), EntityPitch(n\Collider), 10.0), CurveAngle(EntityYaw(Pvt), EntityYaw(n\Collider), 10.0), 0.0, True)
								
								PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider) + 0.8, EntityZ(n\Collider))
								PointEntity(Pvt, me\Collider)
								RotateEntity(Pvt, Min(EntityPitch(Pvt), 40.0), EntityYaw(n\Collider), 0.0)
								
								If n\Reload = 0.0
									EntityPick(Pvt, Dist)
									If PickedEntity() = me\Collider Lor n\State3 = 1.0 Then
										InstaKillPlayer = False
										
										msg\DeathMsg = ""
										
										PlaySound2(GunshotSFX, Camera, n\Collider, 15.0)
										
										RotateEntity(Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0, True)
										PositionEntity(Pvt, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
										MoveEntity(Pvt, 0.8 * 0.079, 10.75 * 0.079, 6.9 * 0.079)
										
										PointEntity(Pvt, me\Collider)
										Shoot(EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), ((25.0 / Dist) * (1.0 / Dist)), True, InstaKillPlayer)
										n\Reload = 7.0
									Else
										n\CurrSpeed = n\Speed
									EndIf
								EndIf
								
								If n\Reload > 0.0 And n\Reload =< 7.0
								    If n\UseEarphones Then
								        AnimateNPC(n, 867.0, 870.0, 0.35, True)
								    Else
									    AnimateNPC(n, 245.0, 248.0, 0.35, True)
									EndIf
								Else
								    If n\UseEarphones Then
								        If n\Frame < 884.0 Then
										    AnimateNPC(n, 884.0, 926.0, 0.35, True)
									    EndIf
									Else
									    If n\Frame < 302.0 Then
									        AnimateNPC(n, 302.0, 344.0, 0.35, True)
									    EndIf
									EndIf
								EndIf
								
								FreeEntity(Pvt)
							Else
								If n\PathStatus = 1 Then
									If n\Path[n\PathLocation] = Null Then 
										If n\PathLocation > 19 Then 
											n\PathLocation = 0 : n\PathStatus = 0
										Else
											n\PathLocation = n\PathLocation + 1
										EndIf
									Else
										If n\UseEarphones Then
									        AnimateNPC(n, 787.0, 823.0, n\CurrSpeed * 40.0)
									    Else
										    AnimateNPC(n, 39.0, 76.0, n\CurrSpeed * 40.0)
										EndIf
										n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
										MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
										
										PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
										
										RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
										
										If EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ) < 0.04 Then
											n\PathLocation = n\PathLocation + 1
										EndIf
									EndIf
								Else
									If n\PathTimer = 0.0 Then n\PathStatus = FindPath(n, EntityX(me\Collider), EntityY(me\Collider) + 0.5, EntityZ(me\Collider))
									
									Local wayPointCloseToPlayer.WayPoints
									
									wayPointCloseToPlayer = Null
									
									For wp.WayPoints = Each WayPoints
										If EntityDistanceSquared(wp\OBJ, me\Collider) < 4.0 Then
											wayPointCloseToPlayer = wp
											Exit
										EndIf
									Next
									
									If wayPointCloseToPlayer <> Null
										n\PathTimer = 1.0
										If EntityVisible(wayPointCloseToPlayer\OBJ, n\Collider) Then
											If Abs(DeltaYaw(n\Collider, wayPointCloseToPlayer\OBJ)) > 0.0 Then
												PointEntity(n\OBJ, wayPointCloseToPlayer\OBJ)
												RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
											EndIf
										EndIf
									Else
										n\PathTimer = 0.0
									EndIf
									
									If n\PathTimer = 1.0 Then
										If n\UseEarphones Then
									        AnimateNPC(n, 787.0, 823.0, n\CurrSpeed * 40.0)
									    Else
										    AnimateNPC(n, 39.0, 76.0, n\CurrSpeed * 40.0)
										EndIf
										n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
										MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
									EndIf
								EndIf
								
								If PrevFrame < 43.0 And n\Frame >= 43.0 Then
									PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))						
								ElseIf PrevFrame < 61.0 And n\Frame >= 61.0
									PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))
								EndIf
							EndIf
						Else
							n\State = 0.0
						EndIf
						;[End Block]
					Case 12.0
						;[Block]
						If n\Frame < 39.0 Lor (n\Frame > 76.0 And n\Frame < 245.0) Lor (n\Frame > 248.0 And n\Frame < 302.0) Lor n\Frame > 344.0 Then
							AnimateNPC(n, 345.0, 357.0, 0.2, False)
							If n\Frame >= 356.0 Then SetNPCFrame(n, 302.0)
						EndIf
						If n\Frame < 345.0
							AnimateNPC(n, 302.0, 344.0, 0.35, True)
						EndIf
						
						Pvt = CreatePivot()
						PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider), EntityZ(n\Collider))
						If n\State2 = 1.0
							PointEntity(Pvt, me\Collider)
						Else
							RotateEntity(Pvt, 0.0, n\Angle, 0.0)
						EndIf
						RotateEntity(Pvt, Min(EntityPitch(Pvt), 20.0), EntityYaw(Pvt), 0.0)
						
						RotateEntity(n\Collider, CurveAngle(EntityPitch(Pvt), EntityPitch(n\Collider), 10.0), CurveAngle(EntityYaw(Pvt), EntityYaw(n\Collider), 10.0), 0.0, True)
						
						PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider) + 0.8, EntityZ(n\Collider))
						If n\State2 = 1.0
							PointEntity(Pvt, me\Collider)
							n\ManipulateBone = True
							n\BoneToManipulate = "Chest"
							n\ManipulationType = 0
						Else
							RotateEntity(Pvt, 0.0, n\Angle, 0.0)
						EndIf
						RotateEntity(Pvt, Min(EntityPitch(Pvt), 40.0), EntityYaw(n\Collider), 0.0)
						
						FreeEntity(Pvt)
						
						UpdateSoundOrigin(n\SoundCHN, Camera, n\Collider, 20.0)
						;[End Block]
					Case 13.0
						;[Block]
						AnimateNPC(n, 202.0, 244.0, 0.35, True)
						;[End Block]
					Case 14.0
						;[Block]
						If n\PathStatus = 2 Then
							n\State = 13.0
							n\CurrSpeed = 0.0
						ElseIf n\PathStatus = 1
							If n\Path[n\PathLocation] = Null Then 
								If n\PathLocation > 19 Then 
									n\PathLocation = 0 : n\PathStatus = 0
								Else
									n\PathLocation = n\PathLocation + 1
								EndIf
							Else
								PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
								
								RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
								
								AnimateNPC(n, 39.0, 76.0, n\CurrSpeed * 40.0)
								n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
								
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								
								If EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ) < 0.04 Then
									n\PathLocation = n\PathLocation + 1
								EndIf 
							EndIf
						Else
							n\CurrSpeed = 0.0
							n\State = 13.0
						EndIf
						
						If PrevFrame < 43.0 And n\Frame >= 43.0 Then
							PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))						
						ElseIf PrevFrame < 61.0 And n\Frame >= 61.0
							PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))
						EndIf
						;[End Block]
					Default
						;[Block]
						If Rand(400) = 1 Then n\PrevState = Rand(-30, 30)
						n\PathStatus = 0
						AnimateNPC(n, 77.0, 201.0, 0.2)
						
						RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle + n\PrevState + Sin(MilliSecs() / 50) * 2.0, EntityYaw(n\Collider), 50.0), 0.0, True)
						;[End Block]
				End Select
				
				If n\CurrSpeed > 0.01 Then
					If PrevFrame < 5.0 And n\Frame >= 5.0 Then
						PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))						
					ElseIf PrevFrame < 23.0 And n\Frame >= 23.0
						PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))						
					EndIf
				EndIf
				
				If n\Frame > 286.5 And n\Frame < 288.5 Then
					n\IsDead = True
				EndIf
				
				n\Reload = Max(0.0, n\Reload - fps\FPSFactor[0])
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.2, EntityZ(n\Collider))
				
				RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider) + 180.0, 0.0)
				;[End Block]
			Case NPCtypeMTF
				;[Block]
				UpdateMTFUnit(n)
				;[End Block]
			Case NPCtypeD, NPCtypeClerk
				;[Block]
				RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), EntityRoll(n\Collider), True)
				
				PrevFrame = AnimTime(n\OBJ)
				
				Select n\State
					Case 0.0 ; ~ Idles
						;[Block]
						n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 5.0)
						Animate2(n\OBJ, AnimTime(n\OBJ), 210.0, 235.0, 0.1)
						;[End Block]
					Case 1.0 ; ~ Walking
						;[Block]
						If n\State2 = 1.0
							n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
						Else
							n\CurrSpeed = CurveValue(0.015, n\CurrSpeed, 5.0)
						EndIf
						Animate2(n\OBJ, AnimTime(n\OBJ), 236.0, 260.0, n\CurrSpeed * 18.0)
						;[End Block]
					Case 2.0 ; ~ Running
						;[Block]
						n\CurrSpeed = CurveValue(0.03, n\CurrSpeed, 5.0)
						Animate2(n\OBJ, AnimTime(n\OBJ), 301.0, 319.0, n\CurrSpeed * 18.0)
						;[End Block]
				End Select
				
				If n\State2 <> 2.0
					If n\State = 1.0
						If n\CurrSpeed > 0.01 Then
							If PrevFrame < 244.0 And AnimTime(n\OBJ) >= 244.0 Then
								PlaySound2(StepSFX(GetStepSound(n\Collider), 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.3, 0.5))						
							ElseIf PrevFrame < 256.0 And AnimTime(n\OBJ) >= 256.0
								PlaySound2(StepSFX(GetStepSound(n\Collider), 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.3, 0.5))
							EndIf
						EndIf
					ElseIf n\State = 2.0
						If n\CurrSpeed > 0.01 Then
							If PrevFrame < 309.0 And AnimTime(n\OBJ) >= 309.0
								PlaySound2(StepSFX(GetStepSound(n\Collider), 1, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.3, 0.5))
							ElseIf PrevFrame =< 319.0 And AnimTime(n\OBJ) =< 301.0
								PlaySound2(StepSFX(GetStepSound(n\Collider), 1, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.3, 0.5))
							EndIf
						EndIf
					EndIf
				EndIf
				
				If n\Frame = 19.0 Lor n\Frame = 40.0 Lor n\Frame = 60.0 Lor n\Frame = 629.0 Lor n\Frame = 677.0 Lor n\Frame = 711.0 Lor n\Frame = 779.0 Then
					n\IsDead = True
				EndIf
				If AnimTime(n\OBJ) = 19.0 Lor AnimTime(n\OBJ) = 40.0 Lor AnimTime(n\OBJ) = 60.0 Lor AnimTime(n\OBJ) = 629.0 Lor AnimTime(n\OBJ) = 677.0 Lor AnimTime(n\OBJ) = 711.0 Lor AnimTime(n\OBJ) = 779.0 Then
					n\IsDead = True
				EndIf
				
				MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.32, EntityZ(n\Collider))
				
				RotateEntity(n\OBJ, EntityPitch(n\Collider), EntityYaw(n\Collider) - 180.0, 0.0)
				;[End Block]
			Case NPCtype513_1
				;[Block]
				If PlayerRoom\RoomTemplate\Name <> "pocketdimension" Then 
					If n\Idle Then
						HideEntity(n\OBJ)
						HideEntity(n\OBJ2)
						If Rand(200) = 1 Then
							For w.WayPoints = Each WayPoints
								If w\room <> PlayerRoom Then
									x = Abs(EntityX(me\Collider) - EntityX(w\OBJ, True))
									If x > 3.0 And x < 9.0 Then
										z = Abs(EntityZ(me\Collider) - EntityZ(w\OBJ, True))
										If z > 3.0 And z < 9.0 Then
											PositionEntity(n\Collider, EntityX(w\OBJ, True), EntityY(w\OBJ, True), EntityZ(w\OBJ, True))
											PositionEntity(n\OBJ, EntityX(w\OBJ, True), EntityY(w\OBJ, True), EntityZ(w\OBJ, True))
											ResetEntity(n\Collider)
											ShowEntity(n\OBJ)
											ShowEntity(n\OBJ2)
											
											n\LastSeen = 0
											
											n\Path[0] = w
											
											n\Idle = 0
											n\State2 = 70.0 * Rnd(15.0, 20.0)
											n\State = Max(Rand(-1.0, 2.0), 0.0)
											n\PrevState = Rand(0, 1)
											Exit
										EndIf
									EndIf
								EndIf
							Next
						EndIf
					Else
						Dist = EntityDistance(me\Collider, n\Collider)
						
						; ~ Use the prev-values to do a "twitching" effect
						n\PrevX = CurveValue(0.0, n\PrevX, 10.0)
						n\PrevZ = CurveValue(0.0, n\PrevZ, 10.0)
						
						If Rand(100) = 1 Then
							If Rand(5) = 1 Then
								n\PrevX = (EntityX(me\Collider) - EntityX(n\Collider)) * 0.9
								n\PrevZ = (EntityZ(me\Collider) - EntityZ(n\Collider)) * 0.9
							Else
								n\PrevX = Rnd(0.1, 0.5)
								n\PrevZ = Rnd(0.1, 0.5)						
							EndIf
						EndIf
						
						Temp = Rnd(-1.0, 1.0)
						PositionEntity(n\OBJ2, EntityX(n\Collider) + n\PrevX * Temp, EntityY(n\Collider) - 0.2 + Sin((MilliSecs() / 8 - 45) Mod 360) * 0.05, EntityZ(n\Collider) + n\PrevZ * Temp)
						RotateEntity(n\OBJ2, 0.0, EntityYaw(n\OBJ), 0.0)
						If Floor(AnimTime(n\OBJ2)) <> Floor(n\Frame) Then SetAnimTime(n\OBJ2, n\Frame)
						
						If n\State = 0.0 Then
							If n\PrevState = 0
								AnimateNPC(n, 2.0, 74.0, 0.2)
							Else
								AnimateNPC(n, 75.0, 124.0, 0.2)
							EndIf
							
							If n\LastSeen Then 	
								PointEntity(n\OBJ2, me\Collider)
								RotateEntity(n\OBJ, 0.0, CurveAngle(EntityYaw(n\OBJ2), EntityYaw(n\OBJ), 40.0), 0.0)
								If Dist < 4.0 Then n\State = Rand(1.0, 2.0)
							Else
								If Dist < 6.0 And Rand(5) = 1 Then
									If EntityInView(n\Collider, Camera) Then
										If EntityVisible(me\Collider, n\Collider) Then
											n\LastSeen = 1
											PlaySound_Strict(LoadTempSound("SFX\SCP\513_1\Bell" + Rand(1, 3) + ".ogg"))
										EndIf
									EndIf
								EndIf								
							EndIf
						Else
							If n\Path[0] = Null Then
								; ~ Move towards a waypoint that is:
								; ~ 1: Max 8 units away from SCP-513-1
								; ~ 2: Further away from the player than SCP-513-1's current position 
								For w.WayPoints = Each WayPoints
									x = Abs(EntityX(n\Collider, True) - EntityX(w\OBJ, True))
									If x < 8.0 And x > 1.0 Then
										z = Abs(EntityZ(n\Collider, True) - EntityZ(w\OBJ, True))
										If z < 8.0 And z > 1.0 Then
											If EntityDistanceSquared(me\Collider, w\OBJ) > PowTwo(Dist) Then
												n\Path[0] = w
												Exit
											EndIf
										EndIf
									EndIf
								Next
								
								; ~ SCP-513-1 simply disappears
								If n\Path[0] = Null Then
									n\Idle = 1
									n\State2 = 0.0
								EndIf
							Else
								If EntityDistanceSquared(n\Collider, n\Path[0]\OBJ) > 1.21 Then
									PointEntity(n\OBJ, n\Path[0]\OBJ)
									RotateEntity(n\Collider, CurveAngle(EntityPitch(n\OBJ), EntityPitch(n\Collider), 15.0), CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 15.0), 0.0, True)
									n\CurrSpeed = CurveValue(0.05 * Max((7.0 - Dist) / 7.0, 0.0), n\CurrSpeed, 15.0)
									MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
									If Rand(200) = 1 Then MoveEntity(n\Collider, 0.0, 0.0, 0.5)
									RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), 0.0, True)
								Else
									For i = 0 To 4
										If n\Path[0]\connected[i] <> Null Then
											If EntityDistanceSquared(me\Collider, n\Path[0]\connected[i]\OBJ) > PowTwo(Dist) Then
												If n\LastSeen = 0 Then 
													If EntityInView(n\Collider, Camera) Then
														If EntityVisible(me\Collider, n\Collider) Then
															n\LastSeen = 1
															PlaySound_Strict(LoadTempSound("SFX\SCP\513_1\Bell" + Rand(1, 3) + ".ogg"))
														EndIf
													EndIf
												EndIf
												n\Path[0] = n\Path[0]\connected[i]
												Exit
											EndIf
										EndIf
									Next
									If n\Path[0] = Null Then n\State2 = 0.0
								EndIf
							EndIf
						EndIf
						PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.2 + Sin((MilliSecs() / 8) Mod 360) * 0.1, EntityZ(n\Collider))
						
						Select n\State 
							Case 1.0
								;[Block]
								If n\PrevState = 0 Then
									AnimateNPC(n, 125.0, 194.0, n\CurrSpeed * 20.0)
								Else
									AnimateNPC(n, 195.0, 264.0, n\CurrSpeed * 20.0)
								EndIf
								RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider), 0.0) 
								;[End Block]
							Case 2.0
								;[Block]
								If n\PrevState = 0 Then
									AnimateNPC(n, 2.0, 74.0, 0.2)
								Else
									AnimateNPC(n, 75.0, 124.0, 0.2)
								EndIf
								RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider), 0.0)
								;[End Block]
						End Select
						
						If n\State2 > 0.0 Then
							If Dist < 4.0 Then n\State2 = n\State2 - fps\FPSFactor[0] * 4.0
							n\State2 = n\State2 - fps\FPSFactor[0]
						Else
							n\Path[0] = Null
							n\Idle = 1
							n\State2 = 0.0
						EndIf
					EndIf
				EndIf
				
				n\DropSpeed = 0.0
				ResetEntity(n\Collider)						
				;[End Block]
			Case NPCtype372
				;[Block]
				RN = PlayerRoom\RoomTemplate\Name
				If RN <> "pocketdimension" And RN <> "dimension1499" Then 
					If n\Idle Then
						HideEntity(n\OBJ)
						If Rand(50) = 1 And (me\BlinkTimer < -5.0 And me\BlinkTimer > -15.0) Then
							ShowEntity(n\OBJ)
							Angle = EntityYaw(me\Collider) + Rnd(-90.0, 90.0)
							
							Dist = Rnd(1.5, 2.0)
							PositionEntity(n\Collider, EntityX(me\Collider) + Sin(Angle) * Dist, EntityY(me\Collider) + 0.2, EntityZ(me\Collider) + Cos(Angle) * Dist)
							n\Idle = 0
							n\State = Rnd(20.0, 60.0)
							
							If Rand(300) = 1 Then PlaySound2(RustleSFX[Rand(0, 5)], Camera, n\OBJ, 8.0, Rnd(0.0, 0.2))
						EndIf
					Else
						PositionEntity(n\OBJ, EntityX(n\Collider) + Rnd(-0.005, 0.005), EntityY(n\Collider) + 0.3 + 0.1 * Sin(MilliSecs() / 2.0), EntityZ(n\Collider) + Rnd(-0.005, 0.005))
						RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider), ((MilliSecs() / 5.0) Mod 360.0))
						
						AnimateNPC(n, 1.0, 300.0, Rnd(0.8, 2.5))
						
						If EntityInView(n\OBJ, Camera) Then
							GiveAchievement(Achv372)
							
							If Rand(30) = 1 Then 
								If (Not ChannelPlaying(n\SoundCHN)) Then
									If EntityVisible(Camera, n\OBJ) Then 
										n\SoundCHN = PlaySound2(RustleSFX[Rand(0, 5)], Camera, n\OBJ, 8.0, 0.3)
									EndIf
								EndIf
							EndIf
							
							Temp = CreatePivot()
							PositionEntity(Temp, EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
							PointEntity(Temp, n\Collider)
							
							Angle =  WrapAngle(EntityYaw(me\Collider) - EntityYaw(Temp))
							If Angle < 180.0 Then
								RotateEntity(n\Collider, 0.0, EntityYaw(me\Collider) - 80.0, 0.0)		
							Else
								RotateEntity(n\Collider, 0.0, EntityYaw(me\Collider) + 80.0, 0.0)
							EndIf
							FreeEntity(Temp)
							
							MoveEntity(n\Collider, 0.0, 0.0, 0.03 * fps\FPSFactor[0])
						EndIf
						n\State = n\State - (fps\FPSFactor[0] * 0.8)
						If n\State =< 0.0 Then n\Idle = 1	
					EndIf
				EndIf
				
				n\DropSpeed = 0.0
				ResetEntity(n\Collider)						
				;[End Block]
			Case NPCtypeApache
				;[Block]
				Dist = EntityDistance(me\Collider, n\Collider)
				If Dist < 60.0 Then 
					If PlayerRoom\RoomTemplate\Name = "gateb" Then 
						Dist2 = Max(Min(EntityDistance(n\Collider, PlayerRoom\Objects[3]) / (8000.0 * RoomScale), 1.0), 0.0)
					Else 
						Dist2 = 1.0
					EndIf
					n\SoundCHN = LoopSound2(ApacheSFX, n\SoundCHN, Camera, n\Collider, 25.0, Dist2)
				EndIf
				
				n\DropSpeed = 0.0
				
				Select n\State
					Case 0.0, 1.0
						;[Block]
						TurnEntity(n\OBJ2, 0.0, 20.0 * fps\FPSFactor[0], 0.0)
						TurnEntity(n\OBJ3, 20.0 * fps\FPSFactor[0], 0.0, 0.0)
						
						If n\State = 1.0 And (Not chs\NoTarget) Then
							If Abs(EntityX(me\Collider) - EntityX(n\Collider)) < 30.0 Then
								If Abs(EntityZ(me\Collider) - EntityZ(n\Collider)) < 30.0 Then
									If Abs(EntityY(me\Collider) - EntityY(n\Collider)) < 20.0 Then
										If Rand(20) = 1 Then 
											If EntityVisible(me\Collider, n\Collider) Then
												n\State = 2.0
												PlaySound2(AlarmSFX[2], Camera, n\Collider, 50.0, 1.0)
											EndIf
										EndIf									
									EndIf
								EndIf
							EndIf							
						EndIf
						;[End Block]
					Case 2.0, 3.0 ; ~ Attacks
						;[Block]
						If n\State = 2.0 Then 
							Target = me\Collider
						ElseIf n\State = 3.0
							Target = CreatePivot()
							PositionEntity(Target, n\EnemyX, n\EnemyY, n\EnemyZ, True)
						EndIf
						
						If chs\NoTarget And n\State = 2.0 Then n\State = 1.0
						
						TurnEntity(n\OBJ2, 0.0, 20.0 * fps\FPSFactor[0], 0.0)
						TurnEntity(n\OBJ3, 20.0 * fps\FPSFactor[0], 0.0, 0.0)
						
						If Abs(EntityX(Target) - EntityX(n\Collider)) < 55.0 Then
							If Abs(EntityZ(Target) - EntityZ(n\Collider)) < 55.0 Then
								If Abs(EntityY(Target) - EntityY(n\Collider)) < 20.0 Then
									PointEntity(n\OBJ, Target)
									RotateEntity(n\Collider, CurveAngle(Min(WrapAngle(EntityPitch(n\OBJ)), 40.0), EntityPitch(n\Collider), 40.0), CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 90.0), EntityRoll(n\Collider), True)
									PositionEntity(n\Collider, EntityX(n\Collider), CurveValue(EntityY(Target) + 8.0, EntityY(n\Collider), 70.0), EntityZ(n\Collider))
									
									Dist = Distance(EntityX(Target), EntityX(n\Collider), EntityZ(Target), EntityZ(n\Collider))
									
									n\CurrSpeed = CurveValue(Min(Dist - 6.5, 6.5) * 0.008, n\CurrSpeed, 50.0)
									
									MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
									
									If n\PathTimer = 0.0 Then
										n\PathStatus = EntityVisible(n\Collider, Target)
										n\PathTimer = Rnd(100.0, 200.0)
									Else
										n\PathTimer = Min(n\PathTimer - fps\FPSFactor[0], 0.0)
									EndIf
									
									If n\PathStatus = 1 Then ; ~ Player visible
										RotateEntity(n\Collider, EntityPitch(n\Collider), EntityYaw(n\Collider), CurveAngle(0.0, EntityRoll(n\Collider), 40.0), True)
										If n\Reload =< 0.0 Then
											If Dist < 20.0 Then
												Pvt = CreatePivot()
												PositionEntity(Pvt, EntityX(n\Collider), EntityY(n\Collider), EntityZ(n\Collider))
												RotateEntity(Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), EntityRoll(n\Collider))
												MoveEntity(Pvt, 0.0, 8.87 * (0.21 / 9.0), 8.87 * (1.7 / 9.0))
												PointEntity(Pvt, Target)
												
												If WrapAngle(EntityYaw(Pvt) - EntityYaw(n\Collider)) < 10.0 Then
													PlaySound2(Gunshot2SFX, Camera, n\Collider, 20.0)
													
													If PlayerRoom\RoomTemplate\Name = "gateb" Then
														msg\DeathMsg = Chr(34) + "CH-2 to control. Shot down a runaway Class D at Gate B." + Chr(34)
													Else
														msg\DeathMsg = Chr(34) + "CH-2 to control. Shot down a runaway Class D at Gate A." + Chr(34)
													EndIf
													
													Shoot(EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), ((10.0 / Dist) * (1.0 / Dist)) * (n\State = 2.0), (n\State = 2.0))
													
													n\Reload = 5.0
												EndIf
												
												FreeEntity(Pvt)
											EndIf
										EndIf
									Else 
										RotateEntity(n\Collider, EntityPitch(n\Collider), EntityYaw(n\Collider), CurveAngle(-20.0, EntityRoll(n\Collider), 40.0), True)
									EndIf
									MoveEntity(n\Collider, (-EntityRoll(n\Collider)) * 0.002, 0.0, 0.0)
									
									n\Reload = n\Reload - fps\FPSFactor[0]
								EndIf
							EndIf
						EndIf		
						
						If n\State = 3 Then FreeEntity(Target)
						;[End Block]
					Case 4.0 ; ~ Crashes
						;[Block]
						If n\State2 < 300.0 Then
							TurnEntity(n\OBJ2, 0.0, 20.0 * fps\FPSFactor[0], 0.0)
							TurnEntity(n\OBJ3, 20.0 * fps\FPSFactor[0], 0.0, 0.0)
							
							TurnEntity(n\Collider, 0.0, (-fps\FPSFactor[0]) * 7.0, 0.0)
							n\State2 = n\State2 + fps\FPSFactor[0] * 0.3
							
							Target = CreatePivot()
							PositionEntity(Target, n\EnemyX, n\EnemyY, n\EnemyZ, True)
							
							PointEntity(n\OBJ, Target)
							MoveEntity(n\OBJ, 0.0, 0.0, fps\FPSFactor[0] * 0.001 * n\State2)
							PositionEntity(n\Collider, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
							
							If EntityDistanceSquared(n\OBJ, Target) < 0.09 Then
								me\CameraShake = Max(me\CameraShake, 3.0)
								PlaySound_Strict(LoadTempSound("SFX\Character\Apache\Crash" + Rand(1, 2) + ".ogg"))
								n\State = 5.0
							EndIf
							FreeEntity(Target)
						EndIf
						;[End Block]
				End Select
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider), EntityZ(n\Collider))
				RotateEntity(n\OBJ, EntityPitch(n\Collider), EntityYaw(n\Collider), EntityRoll(n\Collider), True)
				;[End Block]
			Case NPCtype035_Tentacle
				;[Block]
				Dist = EntityDistanceSquared(n\Collider, me\Collider)
				
				If Dist < PowTwo(HideDistance) Then
					If (Not n\IsDead) Then
					    Select n\State 
						    Case 0.0 ; ~ Spawns
								;[Block]
							    If n\Frame > 283.0 Then
								    me\HeartBeatVolume = Max(CurveValue(1.0, me\HeartBeatVolume, 50.0), me\HeartBeatVolume)
								    me\HeartBeatRate = Max(CurveValue(130.0, me\HeartBeatRate, 100.0), me\HeartBeatRate)
									
								    PointEntity(n\OBJ, me\Collider)
								    RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 25.0), 0.0)
									
								    AnimateNPC(n, 283.0, 389.0, 0.3, False)
									
								    If n\Frame > 388.0 Then
									    n\State = 1.0
										FreeSound_Strict(n\Sound) : n\Sound = 0
									EndIf
							    Else
								    If Dist < 6.25 Then 
									    SetNPCFrame(n, 284.0)
									    n\Sound = LoadSound_Strict("SFX\SCP\035_Tentacle\TentacleSpawn.ogg")
									    PlaySound_Strict(n\Sound)
								    EndIf
							    EndIf
								;[End Block]
						    Case 1.0 ; ~ Idles
								;[Block]
							    If (Not n\Sound) Then
								    FreeSound_Strict(n\Sound) : n\Sound = 0
								    n\Sound = LoadSound_Strict("SFX\SCP\035_Tentacle\TentacleIdle.ogg")
							    EndIf
							    n\SoundCHN = LoopSound2(n\Sound, n\SoundCHN, Camera, n\Collider)
								
							    If Dist < 3.24 And (Not chs\NoTarget) Then
								    If Abs(DeltaYaw(n\Collider, me\Collider)) < 20.0 Then 
									    n\State = 2.0
									    If n\Sound <> 0 Then 
											FreeSound_Strict(n\Sound) : n\Sound = 0 
										EndIf
								    EndIf
									
									PointEntity(n\OBJ, me\Collider)
									RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 25.0), 0.0)
								Else
									; ~ Randomly rotates
									If Rand(400) = 1 Then n\Angle = Rnd(360.0)
									
									RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle + Sin(MilliSecs() / 50) * 2.0, EntityYaw(n\Collider), 150.0), 0.0, True)
							    EndIf
								
							    AnimateNPC(n, 33.0, 174.0, 0.3)
								;[End Block]
						    Case 2.0 ; ~ Attacks
								;[Block]
							    ; ~ Finish the idle animation before playing the attack animation
							    If n\Frame > 33.0 And n\Frame < 174.0 Then
								    AnimateNPC(n, 33.0, 174.0, 2.0, False)
							    Else
								    PointEntity(n\OBJ, me\Collider)
								    RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0.0)							
									
								    If n\Frame > 33.0 Then n\Frame = 2.0
									
								    AnimateNPC(n, 2.0, 32.0, 0.3, False)
									
								    If n\Frame >= 5.0 And n\Frame < 6.0 Then
									    If Dist < 3.24 Then
										    If Abs(DeltaYaw(n\Collider, me\Collider)) < 20.0 Then 
											    If wi\HazmatSuit > 0 Then
											        PlaySound_Strict(LoadTempSound("SFX\General\BodyFall.ogg"))
													InjurePlayer(Rnd(0.5))
												Else
												    PlaySound_Strict(DamageSFX[Rand(9, 10)])
													InjurePlayer(Rnd(1.0, 1.5), 0.0, 100.0, Rnd(0.1, 0.55), 0.2)
													
													If me\Injuries > 3.0 Then
													    If PlayerRoom\RoomTemplate\Name = "room2offices" Then
														    msg\DeathMsg = Chr(34) + "One large and highly active tentacle-like appendage seems "
														    msg\DeathMsg = msg\DeathMsg + "to have grown outside the dead body of a scientist within office area [DATA REDACTED]. It's level of aggression is "
														    msg\DeathMsg = msg\DeathMsg + "unlike anything we've seen before - it looks like it has "
														    msg\DeathMsg = msg\DeathMsg + "beaten some unfortunate Class D to death at some point during the breach." + Chr(34)
													    Else
														    msg\DeathMsg = Chr(34) + "We will need more than the regular cleaning team to take care of this. "
														    msg\DeathMsg = msg\DeathMsg + "Two large and highly active tentacle-like appendages seem "
														    msg\DeathMsg = msg\DeathMsg + "to have formed inside the chamber. Their level of aggression is "
														    msg\DeathMsg = msg\DeathMsg + "unlike anything we've seen before - it looks like they have "
														    msg\DeathMsg = msg\DeathMsg + "beaten some unfortunate Class D to death at some point during the breach." + Chr(34)
													    EndIf
													    Kill(True)
												    EndIf
											    EndIf
											Else
												PlaySound2(MissSFX, Camera, n\Collider)
										    EndIf
										Else
											PlaySound2(MissSFX, Camera, n\Collider, 3.0)
									    EndIf
									    n\Frame = 6.0
								    ElseIf n\Frame = 32.0
									    n\State = 1.0
									    n\Frame = 173.0
								    EndIf
							    EndIf
								;[End Block]
					    End Select
					Else
						; ~ The NPC was killed
					    AnimateNPC(n, 515.0, 551.0, 0.15, False)
					    If n\Frame >= 550.0 Then
							If n\SoundCHN <> 0 Then
								If ChannelPlaying(n\SoundCHN) = True Then StopChannel(n\SoundCHN)
								If n\Sound <> 0 Then
									FreeSound_Strict(n\Sound) : n\Sound = 0
								EndIf
							EndIf
				            HideEntity(n\OBJ)
							HideEntity(n\Collider)
				        EndIf
					EndIf
				EndIf
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider), EntityZ(n\Collider))
				RotateEntity(n\OBJ, EntityPitch(n\Collider) - 90.0, EntityYaw(n\Collider) - 180.0, EntityRoll(n\Collider), True)
				
				n\DropSpeed = 0.0
				
				ResetEntity(n\Collider)
				;[End Block]
			Case NPCtype860_2
				;[Block]
				If PlayerRoom\RoomTemplate\Name = "room860" Then
					Local fr.Forest = PlayerRoom\fr
					
					Dist = EntityDistance(me\Collider, n\Collider)
					
					If ForestNPC <> 0
						If ForestNPCData[2] = 1.0
							ShowEntity(ForestNPC)
							If n\State <> 1.0
								If (me\BlinkTimer < -8.0 And me\BlinkTimer > -12.0) Lor (Not EntityInView(ForestNPC, Camera))
									ForestNPCData[2] = 0.0
									HideEntity(ForestNPC)
								EndIf
							EndIf
						Else
							HideEntity(ForestNPC)
						EndIf
					EndIf
					
					Select n\State
						Case 0.0 ; ~ Idles (hidden)
							;[Block]
							HideEntity(n\Collider)
							HideEntity(n\OBJ)
							HideEntity(n\OBJ2)
							
							n\State2 = 0.0
							PositionEntity(n\Collider, 0.0, -100.0, 0.0)
							;[End Block]
						Case 1.0 ; ~ Appears briefly behind the trees
							;[Block]
							n\DropSpeed = 0.0
							
							If EntityY(n\Collider) =< -100.0 Then
								; ~ Transform the position of the player to the local coordinates of the forest
								TFormPoint(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0, fr\Forest_Pivot)
								
								; ~ Calculate the indices of the forest cell the player is in
								x = Floor((TFormedX() + 6.0) / 12.0)
								z = Floor((TFormedZ() + 6.0) / 12.0)
								
								; ~ Step through nearby cells
								For x2 = Max(x - 1, 1) To Min(x + 1, GridSize) Step 2
									For z2 = Max(z - 1, 1) To Min(z + 1, GridSize) Step 2
										; ~ Choose an empty cell (not on the path)
										If fr\grid[(z2 * GridSize) + x2] = 0 Then
											; ~ Spawn the monster between the empty cell and the cell the player is in
											TFormPoint(((x2 + x) / 2) * 12.0, 0, ((z2 + z) / 2) * 12.0, fr\Forest_Pivot, 0)
											
											; ~ Keep searching for a more suitable cell
											If EntityInView(n\Collider, Camera) Then
												PositionEntity(n\Collider, 0.0, -110.0, 0.0)
											Else
												PositionEntity(n\Collider, TFormedX(), EntityY(fr\Forest_Pivot, True) + 2.3, TFormedZ())
												x2 = GridSize
												Exit												
											EndIf
										EndIf
									Next
								Next
								
								If EntityY(n\Collider) > -100.0 Then
									PlaySound2(StepSFX(3, 0, Rand(0, 2)), Camera, n\Collider, 15.0, 0.5)
									
									If ForestNPCData[2] <> 1.0 Then ForestNPCData[2] = 0.0
									
									Select Rand(3)
										Case 1
											;[Block]
											PointEntity(n\Collider, me\Collider)
											SetNPCFrame(n, 2.0)
										Case 2
											;[Block]
											PointEntity(n\Collider, me\Collider)
											SetNPCFrame(n, 201.0)
											;[End Block]
										Case 3
											;[Block]
											PointEntity(n\Collider, me\Collider)
											TurnEntity(n\Collider, 0.0, 90.0, 0.0)
											SetNPCFrame(n, 299.0)
											;[End Block]
									End Select
									n\State2 = 0.0
								EndIf
							Else
								ShowEntity(n\OBJ)
								ShowEntity(n\Collider)
								
								PositionEntity(n\Collider, EntityX(n\Collider), EntityY(fr\Forest_Pivot, True) + 2.3, EntityZ(n\Collider))
								
								If ForestNPC <> 0
									If ForestNPCData[2] = 0.0 Then
										Local DocChance% = 0
										Local DocAmount% = 0
										
										For i = 0 To MaxItemAmount - 1
											If Inventory[i] <> Null Then
												Local DocName$ = Inventory[i]\ItemTemplate\Name
												
												If DocName = "Log #1" Lor DocName = "Log #2" Lor DocName = "Log #3" Then
													DocAmount = DocAmount + 1
													DocChance = DocChance + 10 * DocAmount
												EndIf
											EndIf
										Next
										
										If Rand(1, 860 - DocChance) = 1 Then
											ShowEntity(ForestNPC)
											ForestNPCData[2] = 1.0
											If Rand(2) = 1
												ForestNPCData[0] = 0.0
											Else
												ForestNPCData[0] = 2.0
											EndIf
											ForestNPCData[1] = 0.0
											PositionEntity(ForestNPC, EntityX(n\Collider), EntityY(n\Collider) + 0.5, EntityZ(n\Collider))
											RotateEntity(ForestNPC, 0.0, EntityYaw(n\Collider), 0.0)
											MoveEntity(ForestNPC, 0.75, 0.0, 0.0)
											RotateEntity(ForestNPC, 0.0, 0.0, 0.0)
											EntityTexture(ForestNPC, ForestNPCTex, ForestNPCData[0])
										Else
											ForestNPCData[2] = 2.0
										EndIf
									ElseIf ForestNPCData[2] = 1.0
										If ForestNPCData[1] = 0.0
											If Rand(200) = 1 Then
												ForestNPCData[1] = fps\FPSFactor[0]
												EntityTexture(ForestNPC, ForestNPCTex, ForestNPCData[0] + 1.0)
											EndIf
										ElseIf ForestNPCData[1] > 0.0 And ForestNPCData[1] < 5.0
											ForestNPCData[1] = Min(ForestNPCData[1] + fps\FPSFactor[0], 5.0)
										Else
											ForestNPCData[1] = 0
											EntityTexture(ForestNPC, ForestNPCTex, ForestNPCData[0])
										EndIf
									EndIf
								EndIf
								
								If n\State2 = 0.0 Then ; ~ Don't start moving until the player is looking
									If EntityInView(n\Collider, Camera) Then 
										n\State2 = 1.0
										If Rand(8) = 1 Then
											PlaySound2(LoadTempSound("SFX\SCP\860\Cancer" + Rand(0, 2) + ".ogg"), Camera, n\Collider, 20.0)
										EndIf										
									EndIf
								Else
									If n\Frame =< 199.0 Then
										AnimateNPC(n, 2.0, 199.0, 0.5, False)
										If n\Frame = 199.0 Then SetNPCFrame(n, 298.0) : PlaySound2(StepSFX(3, 0, Rand(0, 2)), Camera, n\Collider, 15.0)
									ElseIf n\Frame =< 297.0
										PointEntity(n\Collider, me\Collider)
										
										AnimateNPC(n, 200.0, 297.0, 0.5, False)
										If n\Frame = 297.0 Then SetNPCFrame(n, 298.0) : PlaySound2(StepSFX(3, 0, Rand(0, 2)), Camera, n\Collider, 15.0)
									Else
										Angle = CurveAngle(PointDirection(EntityX(n\Collider), EntityZ(n\Collider), EntityX(me\Collider), EntityZ(me\Collider)), EntityYaw(n\Collider) + 90.0, 20.0)
										
										RotateEntity(n\Collider, 0.0, Angle - 90.0, 0.0, True)
										
										AnimateNPC(n, 298.0, 316.0, n\CurrSpeed * 10.0)
										
										n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 10.0)
										MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
										
										If Dist > 15.0 Then
											PositionEntity(n\Collider, 0.0, -110.0, 0.0)
											n\State = 0.0
											n\State2 = 0.0
										EndIf
									EndIf									
								EndIf
							EndIf
							
							ResetEntity(n\Collider)
							;[End Block]
						Case 2.0 ; ~ Appears on the path and starts to walk towards the player
							;[Block]
							ShowEntity(n\OBJ)
							ShowEntity(n\Collider)
							
							PrevFrame = n\Frame
							
							If EntityY(n\Collider) =< -100.0 Then
								; ~ Transform the position of the player to the local coordinates of the forest
								TFormPoint(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0, fr\Forest_Pivot)
								
								; ~ Calculate the indices of the forest cell the player is in
								x = Floor((TFormedX() + 6.0) / 12.0)
								z = Floor((TFormedZ() + 6.0) / 12.0)
								
								For x2 = Max(x - 1, 1) To Min(x + 1, GridSize)
									For z2 = Max(z - 1, 1) To Min(z + 1, GridSize)
										; ~ Find a nearby cell that's on the path and not the cell the player is in
										If fr\grid[(z2 * GridSize) + x2] > 0 And (x2<>x Lor z2 <> z) And (x2 = x Lor z2 = z) Then
											; ~ Transform the position of the cell back to world coordinates
											TFormPoint(x2 * 12.0, 0.0, z2 * 12.0, fr\Forest_Pivot, 0)
											
											PositionEntity(n\Collider, TFormedX(), EntityY(fr\Forest_Pivot, True) + 1.0, TFormedZ())
											
											If EntityInView(n\Collider, Camera) Then
												me\BlinkTimer = -10.0
											Else
												x2 = GridSize
												Exit
											EndIf
										EndIf
									Next
								Next
							Else
								Angle = CurveAngle(Find860Angle(n, fr), EntityYaw(n\Collider) + 90.0, 80.0)
								
								RotateEntity(n\Collider, 0.0, Angle - 90.0, 0.0, True)
								
								n\CurrSpeed = CurveValue(n\Speed * 0.3, n\CurrSpeed, 50.0)
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								
								AnimateNPC(n, 494.0, 569.0, n\CurrSpeed * 25.0)
								
								If n\State2 = 0.0 Then
									If Dist < 8.0 Then
										If EntityInView(n\Collider, Camera) Then
											If Rand(8) = 1 Then
												PlaySound_Strict(LoadTempSound("SFX\SCP\860\Chase" + Rand(1, 2) + ".ogg"))
												
												PlaySound2(LoadTempSound("SFX\SCP\860\Cancer" + Rand(0, 2) + ".ogg"), Camera, n\Collider)
											EndIf
											n\State2 = 1.0
										EndIf										
									EndIf
								EndIf
								
								If me\CurrSpeed > 0.03 Then ; ~ The player is running
									n\State3 = n\State3 + fps\FPSFactor[0]
									If Rnd(5000.0) < n\State3 Then
										Temp = True
										If n\SoundCHN <> 0 Then
											If ChannelPlaying(n\SoundCHN) Then Temp = False
										EndIf
										If Temp Then
											n\SoundCHN = PlaySound2(LoadTempSound("SFX\SCP\860\Cancer" + Rand(0, 2) + ".ogg"), Camera, n\Collider)
										EndIf
									EndIf
								Else
									n\State3 = Max(n\State3 - fps\FPSFactor[0], 0.0)
								EndIf
								
								If Dist < 4.5 Lor n\State3 > Rnd(200.0, 250.0) Then
									n\SoundCHN = PlaySound2(LoadTempSound("SFX\SCP\860\Cancer" + Rand(3, 5) + ".ogg"), Camera, n\Collider)
									If (Not chs\Notarget) Then
										n\State = 3.0
									Else
										If (PrevFrame < 492.0 And n\Frame >= 492.0) Lor (PrevFrame < 568.0 And n\Frame >= 568.0) Then
											SetNPCFrame(n, 2.0)
											n\State = 4.0
										EndIf
									EndIf
								EndIf
								
								If Dist > 20.0 Then
									n\State = 0.0
									n\State2 = 0.0
									PositionEntity(n\Collider, 0.0, -110.0, 0.0)
								EndIf
							EndIf
							
							If (PrevFrame < 533.0 And n\Frame >= 533.0) Lor (PrevFrame > 568.0 And n\Frame < 2.0) Then
								PlaySound2(StepSFX(3, 0, Rand(0, 2)), Camera, n\Collider, 15.0, 0.6)
							EndIf
							;[End Block]
						Case 3.0 ; ~ Runs towards the player and attacks
							;[Block]
							ShowEntity(n\OBJ)
							ShowEntity(n\Collider)
							
							PrevFrame = n\Frame
							
							Angle = CurveAngle(Find860Angle(n, fr), EntityYaw(n\Collider) + 90.0, 40.0)
							
							RotateEntity(n\Collider, 0.0, Angle - 90.0, 0.0, True)
							
							; ~ If close enough to attack or already attacking, play the attack anim
							If (Dist < 1.1 Lor (n\Frame > 451.0 And n\Frame < 493.0) Lor me\KillTimer < 0.0) Then
								msg\DeathMsg = ""
								
								n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 5.0)
								
								AnimateNPC(n, 451.0, 493.0, 0.5, False)
								
								If (PrevFrame < 461.0 And n\Frame >= 461.0) Then 
									If me\KillTimer >= 0.0 Then Kill(True) : me\KillAnim = 0
									PlaySound_Strict(DamageSFX[11])
								EndIf
								If (PrevFrame < 476.0 And n\Frame >= 476.0) Then PlaySound_Strict(DamageSFX[12])
								If (PrevFrame < 486.0 And n\Frame >= 486.0) Then PlaySound_Strict(DamageSFX[12])
							Else
								n\CurrSpeed = CurveValue(n\Speed * 0.8, n\CurrSpeed, 10.0)
								
								AnimateNPC(n, 298.0, 316.0, n\CurrSpeed * 10.0)
								
								If (PrevFrame < 307.0 And n\Frame >= 307.0) Then
									PlaySound2(StepSFX(3, 0, Rand(0, 2)), Camera, n\Collider, 10.0)
								EndIf
							EndIf
							MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
							
							If chs\Notarget Then
								If (PrevFrame < 315.0 And n\Frame >= 315.0) Lor (PrevFrame < 492.0 And n\Frame >= 492.0) Then
									SetNPCFrame(n, 2.0)
									n\State = 4.0
								EndIf
							EndIf
							;[End Block]
						Case 4.0 ; ~ Idles (not hidden)
							;[Block]
							n\CurrSpeed = 0.0
							
							AnimateNPC(n, 2.0, 199.0, 0.5)
							
							If (Not chs\Notarget) Then n\State = 3.0
							;[End Block]
					End Select
					
					If n\State <> 0.0 Then
						RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), 0.0, True)	
						
						PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.23, EntityZ(n\Collider))
						RotateEntity(n\OBJ, EntityPitch(n\Collider) - 90.0, EntityYaw(n\Collider), EntityRoll(n\Collider), True)
						
						If Dist > 8.0 Then
							ShowEntity(n\OBJ2)
							EntityAlpha(n\OBJ2, Min(Dist - 8.0, 1.0))
							
							PositionEntity(n\OBJ2, EntityX(n\OBJ), EntityY(n\OBJ) , EntityZ(n\OBJ))
							RotateEntity(n\OBJ2, 0.0, EntityYaw(n\Collider) - 180.0, 0.0)
							MoveEntity(n\OBJ2, 0.0, 30.0 * 0.025, (-33.0) * 0.025)
							
							; ~ Render distance is set to 8.5 inside the forest,
							; ~ So we need to cheat a bit to make the eyes visible if they're further than that
							Pvt = CreatePivot()
							PositionEntity(Pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
							PointEntity(Pvt, n\OBJ2)
							MoveEntity(Pvt, 0.0, 0.0, 8.0)
							PositionEntity(n\OBJ2, EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt))
							FreeEntity(Pvt)
						Else
							HideEntity(n\OBJ2)
						EndIf
					EndIf
				EndIf
				;[End Block] 
			Case NPCtype939
				;[Block]
				If PlayerRoom\RoomTemplate\Name <> "room3storage" Then
					n\State = 66.0
				EndIf
				
				; ~ State is set to 66 in the room3storage-event if player isn't inside the room
				If n\State < 66.0 Then 
					Select n\State
						Case 0.0 ; ~ Idles
							;[Block]
							AnimateNPC(n, 290.0, 405.0, 0.1)
							;[End Block]
						Case 1.0
							;[Block]
							If n\Frame >= 644.0 And n\Frame < 683.0 Then
								n\CurrSpeed = CurveValue(n\Speed * 0.05, n\CurrSpeed, 10.0)
								AnimateNPC(n, 644.0, 683.0, 28.0 * n\CurrSpeed * 4.0, False)
								If n\Frame >= 682 Then SetNPCFrame(n, 175.0)
							Else
								n\CurrSpeed = CurveValue(0, n\CurrSpeed, 5.0)
								AnimateNPC(n, 175.0, 297.0, 0.22, False)
								If n\Frame >= 296.0 Then n\State = 2.0
							EndIf
							
							n\LastSeen = 0
							
							MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])						
							;[End Block]
						Case 2.0 ; ~ Walking
							;[Block]
							n\State2 = Max(n\State2, (n\PrevState - 3))
							
							Dist = EntityDistance(n\Collider, PlayerRoom\Objects[n\State2])
							
							n\CurrSpeed = CurveValue(n\Speed * 0.3 * Min(Dist, 1.0), n\CurrSpeed, 10.0)
							MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0]) 
							
							PrevFrame = n\Frame
							AnimateNPC(n, 644.0, 683.0, 28.0 * n\CurrSpeed)
							
							If (PrevFrame < 664.0 And n\Frame >= 664.0) Lor (PrevFrame > 673.0 And n\Frame < 654.0) Then
								PlaySound2(StepSFX(5, 0, Rand(0, 3)), Camera, n\Collider, 12.0)
								If Rand(10) = 1 Then
									Temp = False
									If (Not n\SoundCHN) Then 
										Temp = True
									ElseIf (Not ChannelPlaying(n\SoundCHN))
										Temp = True
									EndIf
									If Temp Then
										If n\Sound <> 0 Then 
											FreeSound_Strict(n\Sound) : n\Sound = 0
										EndIf
										n\Sound = LoadSound_Strict("SFX\SCP\939\" + (n\ID Mod 3) + "Lure" + Rand(1, 10) + ".ogg")
										n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider)
									EndIf
								EndIf
							EndIf
							
							PointEntity(n\OBJ, PlayerRoom\Objects[n\State2])
							RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
							
							If Dist < 0.4 Then
								n\State2 = n\State2 + 1.0
								If n\State2 > n\PrevState Then n\State2 = (n\PrevState - 3)
								n\State = 1.0
							EndIf
							;[End Block]
						Case 3.0
							;[Block]
							If EntityVisible(me\Collider, n\Collider) Then
								n\EnemyX = EntityX(me\Collider)
								n\EnemyZ = EntityZ(me\Collider)
								n\LastSeen = 70.0 * 1.0
							EndIf
							
							If n\LastSeen > 0 And (Not chs\NoTarget) Then
								PrevFrame = n\Frame
								
								If (n\Frame >= 18.0 And n\Frame < 68.0) Then
									n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 5.0)
									AnimateNPC(n, 18.0, 68.0, 0.5, True)
									
									Temp = False
									
									If PrevFrame < 24.0 And n\Frame >= 24.0 Then
										Temp = True
									ElseIf PrevFrame < 57.0 And n\Frame >= 57.0
										Temp = True
									EndIf
									
									If Temp Then
										If DistanceSquared(n\EnemyX, EntityX(n\Collider), n\EnemyZ, EntityZ(n\Collider)) < 2.25 Then
											PlaySound_Strict(DamageSFX[11])
											InjurePlayer(Rnd(1.5, 2.5), 0.0, 500.0, Rnd(0.1, 0.75))
										Else
											SetNPCFrame(n, 449.0)
										EndIf
									EndIf
									
									If me\Injuries > 4.0 Then 
										msg\DeathMsg = Chr(34) + "All four (4) escaped SCP-939 specimens have been captured and recontained successfully. "
										msg\DeathMsg = msg\DeathMsg + "They made quite a mess at Storage Area 6. A cleaning team has been dispatched." + Chr(34)
										Kill(True)
										If (Not chs\GodMode) Then n\State = 5.0
									EndIf								
								Else
									If n\LastSeen = 70.0 * 1.0 Then 
										n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
										
										AnimateNPC(n, 449.0, 464.0, n\CurrSpeed * 6.0)
										
										If (PrevFrame < 452.0 And n\Frame >= 452.0) Lor (PrevFrame < 459.0 And n\Frame >= 459.0) Then
											PlaySound2(StepSFX(1, 1, Rand(0, 7)), Camera, n\Collider, 12.0)
										EndIf										
										
										If DistanceSquared(n\EnemyX, EntityX(n\Collider), n\EnemyZ, EntityZ(n\Collider)) < 1.21 Then ; ~ Player is visible
											SetNPCFrame(n, 18.0)
										EndIf
									Else
										n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 5.0)
										AnimateNPC(n, 175.0, 297.0, n\CurrSpeed * 5.0)	
									EndIf
								EndIf
								
								Angle = VectorYaw(n\EnemyX - EntityX(n\Collider), 0.0, n\EnemyZ - EntityZ(n\Collider))
								RotateEntity(n\Collider, 0.0, CurveAngle(Angle, EntityYaw(n\Collider), 15.0), 0.0)									
								
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])							
								
								n\LastSeen = n\LastSeen - fps\FPSFactor[0]
							Else
								n\State = 2.0
							EndIf
							;[End Block]
						Case 5.0 ; ~ Finishes attack and goes to idle
							;[Block]
							If n\Frame < 68.0 Then
								AnimateNPC(n, 18.0, 68.0, 0.5, False)
							Else
								AnimateNPC(n, 464.0, 473.0, 0.5, False)
							EndIf
							;[End Block]
					End Select
					
					If n\State < 3.0 And (Not chs\NoTarget) And (Not n\IgnorePlayer) Then
						Dist = EntityDistance(n\Collider, me\Collider)
						
						If Dist < 4.0 Then Dist = Dist - EntityVisible(me\Collider, n\Collider)
						If me\SndVolume * 1.2 > Dist Lor Dist < 1.5 Then
							If n\State3 = 0.0 Then
								If n\Sound <> 0 Then 
									FreeSound_Strict(n\Sound) : n\Sound = 0
								EndIf
								n\Sound = LoadSound_Strict("SFX\SCP\939\" + (n\ID Mod 3) + "Attack" + Rand(1, 3) + ".ogg")
								n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider)										
								
								PlaySound_Strict(LoadTempSound("SFX\SCP\939\Attack.ogg"))
								n\State3 = 1.0
							EndIf
							
							n\State = 3.0
						ElseIf me\SndVolume * 1.6 > Dist
							If n\State <> 1 And n\Reload =< 0.0 Then
								If n\Sound <> 0 Then 
									FreeSound_Strict(n\Sound) : n\Sound = 0
								EndIf
								n\Sound = LoadSound_Strict("SFX\SCP\939\" + (n\ID Mod 3) + "Alert" + Rand(1, 3) + ".ogg")
								n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider)	
								
								SetNPCFrame(n, 175.0)
								
								n\Reload = 70.0 * 3.0
							EndIf
							n\State = 1.0
						EndIf
						n\Reload = n\Reload - fps\FPSFactor[0]
					EndIf				
					
					RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), 0.0, True)	
					
					PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.28, EntityZ(n\Collider))
					RotateEntity(n\OBJ, EntityPitch(n\Collider) - 90.0, EntityYaw(n\Collider), EntityRoll(n\Collider), True)				
				EndIf
				;[End Block]
			Case NPCtype066
				;[Block]
				Dist = Distance(EntityX(me\Collider), EntityX(n\Collider), EntityZ(me\Collider), EntityZ(n\Collider))
				
				Select n\State
					Case 0.0
						;[Block]
						; ~ Idles: moves around randomly from waypoint to another if the player is far enough
						; ~ Starts staring at the player when the player is close enough
						
						If Dist > 20.0 Then
							AnimateNPC(n, 451, 612, 0.2, True)
							
							If n\State2 < MilliSecs() Then
								For w.Waypoints = Each WayPoints
									If w\door = Null Then
										If Abs(EntityX(w\OBJ, True) - EntityX(n\Collider)) < 4.0 Then
											If Abs(EntityZ(w\OBJ, True) - EntityZ(n\Collider)) < 4.0 Then
												PositionEntity(n\Collider, EntityX(w\OBJ, True), EntityY(w\OBJ, True) + 0.3, EntityZ(w\OBJ, True))
												ResetEntity(n\Collider)
												Exit
											EndIf
										EndIf
									EndIf
								Next
								n\State2 = MilliSecs() + 5000.0
							EndIf
						ElseIf Dist < 8.0 Then
							n\LastDist = Rnd(1.0, 2.5)
							n\State = 1.0
						EndIf
						;[End Block]
					Case 1.0 ; ~ Staring at the player
						;[Block]
						If n\Frame < 451.0 Then
							Angle = WrapAngle(CurveAngle(DeltaYaw(n\Collider, me\Collider) - 180.0, (AnimTime(n\OBJ) - 2.0) / 1.2445, 15.0))
							
							SetNPCFrame(n, Angle * 1.2445 + 2.0)
						Else
							AnimateNPC(n, 636.0, 646.0, 0.4, False)
							If n\Frame = 646.0 Then SetNPCFrame(n, 2.0)
						EndIf
						Dist = Distance(EntityX(me\Collider), EntityX(n\Collider), EntityZ(me\Collider), EntityZ(n\Collider))
						
						If Rand(700) = 1 Then PlaySound2(LoadTempSound("SFX\SCP\066\Eric" + Rand(1, 3) + ".ogg"), Camera, n\Collider, 8.0)
						
						If Dist < 1.0 + n\LastDist Then n\State = Rand(2.0, 3.0)
						;[End Block]
					Case 2.0 ; ~ Rolls towards the player and make a sound, and then escape	
						;[Block]
						If n\Frame < 647.0 Then 
							Angle = CurveAngle(0.0, (AnimTime(n\OBJ) - 2.0) / 1.2445, 5.0)
							
							If Angle < 5.0 Lor Angle > 355.0 Then 
								SetNPCFrame(n, 647.0)
							Else
								SetNPCFrame(n, Angle * 1.2445 + 2.0)
							EndIf
						Else
							If n\Frame = 683.0 Then 
								If n\State2 = 0.0 Then
									If Rand(2) = 1 Then
										PlaySound2(LoadTempSound("SFX\SCP\066\Eric" + Rand(1, 3) + ".ogg"), Camera, n\Collider, 8.0)
									Else
										PlaySound2(LoadTempSound("SFX\SCP\066\Notes" + Rand(1, 6) + ".ogg"), Camera, n\Collider, 8.0)
									EndIf									
									
									If (Not chs\Notarget) Then
										Select Rand(1, 6)
											Case 1
												;[Block]
												If (Not n\Sound2) Then n\Sound2 = LoadSound_Strict("SFX\SCP\066\Beethoven.ogg")
												n\SoundCHN2 = PlaySound2(n\Sound2, Camera, n\Collider)
												me\DeafTimer = 70.0 * (45.0 + (15.0 * SelectedDifficulty\AggressiveNPCs))
												me\Deaf = True
												me\CameraShake = 10.0
												;[End Block]
											Case 2
												;[Block]
												n\State3 = Rand(700.0, 1400.0)
												;[End Block]
											Case 3
												;[Block]
												For d.Doors = Each Doors
													If d\Locked = 0 And d\KeyCard = 0 And d\Code = "" Then
														If Abs(EntityX(d\FrameOBJ) - EntityX(n\Collider)) < 16.0 Then
															If Abs(EntityZ(d\FrameOBJ) - EntityZ(n\Collider)) < 16.0 Then
																UseDoor(d, False)
															EndIf
														EndIf
													EndIf
												Next
												;[End Block]
											Case 4
												;[Block]
												If (Not PlayerRoom\RoomTemplate\DisableDecals) Then
													me\CameraShake = 5.0
													de.Decals = CreateDecal(1, EntityX(n\Collider), 0.01, EntityZ(n\Collider), 90.0, Rnd(360.0), 0.0, 0.3)
													PlaySound_Strict(LoadTempSound("SFX\General\BodyFall.ogg"))
													If DistanceSquared(EntityX(me\Collider), EntityX(n\Collider), EntityZ(me\Collider), EntityZ(n\Collider)) < 0.64 Then
														InjurePlayer(Rnd(0.3, 0.5), 0.0, 200.0)
													EndIf
												EndIf
												;[End Block]
											Case 5, 6 ; ~ No effect
												;[Block]
												;[End Block]
										End Select
									EndIf
								EndIf
								
								n\State2 = n\State2 + fps\FPSFactor[0]
								If n\State2 > 70.0 Then 
									n\State = 3.0
									n\State2 = 0.0
								EndIf
							Else
								n\CurrSpeed = CurveValue(n\Speed * 1.5, n\CurrSpeed, 10.0)
								PointEntity(n\OBJ, me\Collider)
								
								RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ) - 180.0, EntityYaw(n\Collider), 10.0), 0.0)
								
								AnimateNPC(n, 647.0, 683.0, n\CurrSpeed * 25.0, False)
								
								MoveEntity(n\Collider, 0.0, 0.0, (-n\CurrSpeed) * fps\FPSFactor[0])
							EndIf
						EndIf
						;[End Block]
					Case 3.0
						;[Block]
						PointEntity(n\OBJ, me\Collider)
						Angle = CurveAngle(EntityYaw(n\OBJ) + n\Angle - 180.0, EntityYaw(n\Collider), 10.0)
						RotateEntity(n\Collider, 0.0, Angle, 0.0)
						
						n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 10.0)
						MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
						
						If Rand(100) = 1 Then n\Angle = Rnd(-20.0, 20.0)
						
						n\State2 = n\State2 + fps\FPSFactor[0]
						If n\State2 > 250.0 Then 
							AnimateNPC(n, 684.0, 647.0, (-n\CurrSpeed) * 25.0, False)
							If n\Frame = 647.0 Then 
								n\State = 0.0
								n\State2 = 0.0
							EndIf
						Else
							AnimateNPC(n, 684.0, 647.0, (-n\CurrSpeed) * 25.0)
						EndIf
						;[End Block]
				End Select
				
				If n\State > 1.0 Then
					If (Not n\Sound) Then n\Sound = LoadSound_Strict("SFX\SCP\066\Rolling.ogg")
					If n\SoundCHN <> 0 Then
						If ChannelPlaying(n\SoundCHN) = True Then
							n\SoundCHN = LoopSound2(n\Sound, n\SoundCHN, Camera, n\Collider, 20.0)
						EndIf
					Else
						n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider, 20.0)
					EndIf					
				EndIf
				
				If n\State3 > 0.0 Then
					n\State3 = n\State3 - fps\FPSFactor[0]
					LightVolume = TempLightVolume - TempLightVolume * Min(Max(n\State3 / 500.0, 0.01), 0.6)
					me\HeartBeatRate = Max(me\HeartBeatRate, 130.0)
					me\HeartBeatVolume = Max(me\HeartBeatVolume, Min(n\State3 / 1000.0, 1.0))
				EndIf
				
				If ChannelPlaying(n\SoundCHN2) = True Then
					UpdateSoundOrigin(n\SoundCHN2, Camera, n\Collider, 20.0, 1.0, False)
					me\BlurTimer = Max((5.0 - Dist) * 300.0, 0.0)
				EndIf
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.2, EntityZ(n\Collider))
				
				RotateEntity(n\OBJ, EntityPitch(n\Collider) - 90.0, EntityYaw(n\Collider), 0.0)
				;[End Block]
			Case NPCtype966
				;[Block]
				Dist = EntityDistance(n\Collider, me\Collider)
				
				If n\State > -1.0 Then
					If Dist < HideDistance Then
						; ~ n\State: The "general" state (Idles / Wanders off/ Attacks / Echo and etc.)
						
						; ~ n\State2: Timer for doing raycasts
						
						If EntityVisible(n\Collider, Camera) Then
							If wi\NightVision > 0 Then GiveAchievement(Achv966)
						EndIf
						
						PrevFrame = n\Frame
						
						PositionEntity(n\OBJ, EntityX(n\Collider, True), EntityY(n\Collider, True) - 0.2, EntityZ(n\Collider, True))
						RotateEntity(n\OBJ, -90.0, EntityYaw(n\Collider), 0.0)
						
						If wi\NightVision = 0 Then
							HideEntity(n\OBJ)
							If (Not chs\Notarget) Then
								If Dist < 1.0 And n\Reload =< 0.0 And msg\Timer =< 0.0 Then
									Select Rand(6)
										Case 1
											;[Block]
											CreateMsg("You feel something breathing right next to you.", 6.0)
											;[End Block]
										Case 2
											;[Block]
											CreateMsg(Chr(34) + "It feels like something's in this room with me." + Chr(34), 6.0)
											;[End Block]
										Case 3
											;[Block]
											CreateMsg("You feel like something is here with you, but you do not see anything.", 6.0)
											;[End Block]
										Case 4
											;[Block]
											CreateMsg(Chr(34) + "Is my mind playing tricks on me or is there someone else here?" + Chr(34), 6.0)
											;[End Block]
										Case 5
											;[Block]
											CreateMsg("You feel like something is following you.", 6.0)
											;[End Block]
										Case 6
											;[Block]
											CreateMsg("You can feel something near you, but you are unable to see it. Perhaps its time is now.", 6.0)
											;[End Block]
									End Select
									n\Reload = 70.0 * 20.0
								EndIf
								n\Reload = n\Reload - fps\FPSFactor[0]
							EndIf
						Else
							ShowEntity(n\OBJ)
						EndIf
						
						If n\State3 > 70.0 * 5.0 Then
							If n\State3 < 1000.0 Then
								For n2.NPCs = Each NPCs	
									If n2\NPCtype = n\NPCtype Then n2\State3 = 1000.0 
								Next
							EndIf
							n\State = Max(n\State, 8.0)
							n\State3 = 1000.0					
						EndIf
						
						If me\Stamina < 10.0 Then 
							n\State3 = n\State3 + fps\FPSFactor[0]
						ElseIf n\State3 < 900.0
							n\State3 = Max(n\State3 - fps\FPSFactor[0] * 0.2, 0.0)
						EndIf
						
						If n\State <> 10.0 Then
							n\LastSeen = 0
						EndIf
						
						Select n\State
							Case 0.0 ; ~ Idles
								;[Block]
								If n\Frame > 556.0
									AnimateNPC(n, 628.0, 652.0, 0.25, False)
									If n\Frame > 651.0 Then SetNPCFrame(n, 2.0)
								Else
									AnimateNPC(n, 2.0, 214.0, 0.25, False)
									
									; ~ Echo / Stares off / Walking around periodically
									If n\Frame > 213.0
										If Rand(3) = 1 And Dist < 4.0 Then
											n\State = Rand(1.0, 4.0)
										Else
											n\State = Rand(5.0, 6.0)								
										EndIf
									EndIf
									
									; ~ Echo if player gets close
									If Dist < 2.0 Then 
										n\State = Rand(1, 4)
									EndIf 							
								EndIf
								
								n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 10.0)
								
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed)
								;[End Block]
							Case 1.0, 2.0 ; ~ Echo
								;[Block]
								AnimateNPC(n, 214.0, 257.0, 0.25, False)
								If n\Frame > 256.0 Then n\State = 0.0
								
								If n\Frame > 228.0 And PrevFrame =< 228.0
									PlaySound2(LoadTempSound("SFX\SCP\966\Echo" + Rand(1, 3) + ".ogg"), Camera, n\Collider)
								EndIf
								
								If (Not chs\Notarget) Then
									Angle = VectorYaw(EntityX(me\Collider) - EntityX(n\Collider), 0.0, EntityZ(me\Collider) - EntityZ(n\Collider))
									RotateEntity(n\Collider, 0.0, CurveAngle(Angle, EntityYaw(n\Collider), 20.0), 0.0)
									
									If n\State3 < 900.0 Then
										me\BlurTimer = ((Sin(MilliSecs() / 50) + 1.0) * 200) / Dist
										
										If I_714\Using = 0 And wi\GasMask < 3 And wi\HazmatSuit < 3 And Dist < 16.0 Then
											me\BlinkEffect = Max(me\BlinkEffect, 1.5)
											me\BlinkEffectTimer = 1000.0
											
											me\StaminaEffect = 2.0
											me\StaminaEffectTimer = 1000.0
											
											If msg\Timer =< 0.0 And me\StaminaEffect < 1.5 Then
												Select Rand(4)
													Case 1
														;[Block]
														CreateMsg("You feel exhausted.", 6.0)
														;[End Block]
													Case 2
														;[Block]
														CreateMsg(Chr(34) + "Could really go for a nap now..." + Chr(34), 6.0)
														;[End Block]
													Case 3
														;[Block]
														CreateMsg(Chr(34) + "If I wasn't in this situation I would take a nap somewhere." + Chr(34), 6.0)
														;[End Block]
													Case 4
														;[Block]
														CreateMsg("You feel restless.", 6.0)
														;[End Block]
												End Select
											EndIf
										EndIf							
									EndIf
								EndIf
								;[End Block]
							Case 3.0, 4.0 ; ~ Stare off at player
								;[Block]
								If n\State = 3.0 Then
									AnimateNPC(n, 257.0, 332.0, 0.25, False)
									If n\Frame > 331.0 Then n\State = 0.0
								Else
									AnimateNPC(n, 332.0, 457.0, 0.25, False)
									If n\Frame > 456.0 Then n\State = 0.0
								EndIf
								
								If n\Frame > 271.0 And PrevFrame =< 271.0 Lor n\Frame > 314.0 And PrevFrame =< 314.0 Lor n\Frame > 301.0 And PrevFrame =< 301.0 Then
									PlaySound2(LoadTempSound("SFX\SCP\966\Idle" + Rand(1, 3) + ".ogg"), Camera, n\Collider)
								EndIf
								
								If (Not chs\Notarget) Then
									Angle = VectorYaw(EntityX(me\Collider) - EntityX(n\Collider), 0.0, EntityZ(me\Collider) - EntityZ(n\Collider))
									RotateEntity(n\Collider, 0.0, CurveAngle(Angle, EntityYaw(n\Collider), 20.0), 0.0)
								EndIf
								;[End Block]
							Case 5.0, 6.0, 8.0 ; ~ Walking or chasing
								;[Block]
								If n\Frame < 580.0 And n\Frame > 214.0
									AnimateNPC(n, 556.0, 580.0, 0.25, False)
								Else
									If n\CurrSpeed > 0.0 Then
										AnimateNPC(n, 580.0, 628.0, n\CurrSpeed * 25.0)
									Else
										AnimateNPC(n, 2.0, 214.0, 0.25)
									EndIf
									
									; ~ Chasing the player
									If n\State = 8.0 And Dist < 32.0 Then
										If n\PathTimer =< 0.0 Then
											n\PathStatus = FindPath(n, EntityX(me\Collider, True), EntityY(me\Collider, True), EntityZ(me\Collider, True))
											n\PathTimer = 40 * 10.0
											n\CurrSpeed = 0.0
										EndIf
										n\PathTimer = Max(n\PathTimer - fps\FPSFactor[0], 0.0)
										
										If (Not EntityVisible(n\Collider, me\Collider)) Then
											If n\PathStatus = 2 Then
												n\CurrSpeed = 0.0
												SetNPCFrame(n, 2.0)
											ElseIf n\PathStatus = 1
												If n\Path[n\PathLocation] = Null Then 
													If n\PathLocation > 19 Then 
														n\PathLocation = 0 : n\PathStatus = 0
													Else
														n\PathLocation = n\PathLocation + 1
													EndIf
												Else
													n\Angle = VectorYaw(EntityX(n\Path[n\PathLocation]\OBJ, True) - EntityX(n\Collider), 0.0, EntityZ(n\Path[n\PathLocation]\OBJ, True) - EntityZ(n\Collider))
													
													Dist2 = EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ)
													
													Temp = True
													If Dist2 < 0.64 Then 
														If n\Path[n\PathLocation]\door <> Null Then
															If (Not n\Path[n\PathLocation]\door\IsElevatorDoor)
																If (n\Path[n\PathLocation]\door\Locked = 1 Lor n\Path[n\PathLocation]\door\KeyCard <> 0 Lor n\Path[n\PathLocation]\door\Code <> "") And (Not n\Path[n\PathLocation]\door\Open) Then
																	Temp = False
																Else
																	If (Not n\Path[n\PathLocation]\door\Open) And (n\Path[n\PathLocation]\door\Buttons[0] <> 0 Lor n\Path[n\PathLocation]\door\Buttons[1] <> 0) Then
																		UseDoor(n\Path[n\PathLocation]\door, False)
																	EndIf
																EndIf
															EndIf
														EndIf
														If Dist2 < 0.09 Then n\PathLocation = n\PathLocation + 1
													EndIf
													
													If (Not Temp) Then
														n\PathStatus = 0
														n\PathLocation = 0
														n\PathTimer = 40 * 10.0
													EndIf
												EndIf
												
												n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 10.0)
											ElseIf n\PathStatus = 0
												n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 10.0)
											EndIf
										Else
											n\Angle = VectorYaw(EntityX(me\Collider) - EntityX(n\Collider), 0.0, EntityZ(me\Collider) - EntityZ(n\Collider))
											n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 10.0)
											
											If Dist < 1.0 Then n\State = 10.0
										EndIf
									Else
										If MilliSecs() > n\State2 And Dist < 16.0 Then
											HideEntity(n\Collider) 
											EntityPick(n\Collider, 1.5)
											If PickedEntity() <> 0 Then
												n\Angle = EntityYaw(n\Collider) + Rnd(80.0, 110.0)
											EndIf
											ShowEntity(n\Collider)
											
											n\State2 = MilliSecs() + 1000
											
											If Rand(5) = 1 Then n\State = 0.0
										EndIf	
										
										n\CurrSpeed = CurveValue(n\Speed * 0.5, n\CurrSpeed, 20.0)
									EndIf
									
									If (PrevFrame < 604.0 And n\Frame >= 604.0) Lor (PrevFrame < 627.0 And n\Frame >= 627.0) Then
										PlaySound2(StepSFX(5, 0, Rand(0, 3)), Camera, n\Collider, 7.0, Rnd(0.5, 0.7))
									EndIf
									
									RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle, EntityYaw(n\Collider), 10.0), 0.0)
									
									MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								EndIf
								;[End Block]
							Case 10.0 ; ~ Attacks
								;[Block]
								If chs\Notarget Then n\State = 0.0
								
								If n\LastSeen = 0 Then
									PlaySound2(LoadTempSound("SFX\SCP\966\Echo" + Rand(1, 3) + ".ogg"), Camera, n\Collider)
									n\LastSeen = 1
								EndIf
								
								If n\Frame > 557.0 Then
									AnimateNPC(n, 628, 652, 0.25, False)
									If n\Frame > 651.0
										Select Rand(3)
											Case 1
												;[Block]
												SetNPCFrame(n, 458.0)
												;[End Block]
											Case 2
												;[Block]
												SetNPCFrame(n, 488.0)
												;[End Block]
											Case 3
												;[Block]
												SetNPCFrame(n, 518.0)
												;[End Block]
										End Select
									EndIf
								Else
									If n\Frame =< 487.0 Then
										AnimateNPC(n, 458.0, 487.0, 0.3, False)
										If n\Frame > 486.0 Then n\State = 8.0
									ElseIf n\Frame =< 517.0
										AnimateNPC(n, 488.0, 517.0, 0.3, False)
										If n\Frame > 516.0 Then n\State = 8.0
									ElseIf n\Frame =< 557.0
										AnimateNPC(n, 518.0, 557.0, 0.3, False)
										If n\Frame > 556.0 Then n\State = 8.0
									EndIf
								EndIf
								
								If n\Frame > 470.0 And PrevFrame =< 470.0 Lor n\Frame > 500.0 And PrevFrame =< 500.0 Lor n\Frame > 527.0 And PrevFrame =< 527.0 Then
									If Dist < 1.0 Then
										If Abs(DeltaYaw(n\Collider, me\Collider)) =< 60.0 Then
											PlaySound2(DamageSFX[Rand(11, 12)], Camera, n\Collider)
											InjurePlayer(Rnd(0.5, 1.0), 0.0, 500.0, Rnd(0.1, 0.3))
										EndIf
									Else
										PlaySound2(MissSFX, Camera, n\Collider, 2.5)
									EndIf	
								EndIf
								
								n\Angle = VectorYaw(EntityX(me\Collider) - EntityX(n\Collider), 0.0, EntityZ(me\Collider) - EntityZ(n\Collider))
								RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle, EntityYaw(n\Collider), 30.0), 0.0)
								;[End Block]
						End Select
					Else
						HideEntity(n\OBJ)
						If Rand(600) = 1 Then
							TeleportCloser(n)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case NPCtype1499_1
				;[Block]
				; ~ n\State: Current State of the NPC
				
				; ~ n\State2: A second state variable (dependend on the current NPC's n\State)
				
				; ~ n\State3: Determines if the NPC will always be aggressive against the player
				
				; ~ n\PrevState: Determines the type / behaviour of the NPC
				; ~ 0: Normal / Citizen
				; ~ 1: Stair guard / Guard next to king
				; ~ 2: King
				; ~ 3: Front guard
				
				PrevFrame# = n\Frame
				
				If (Not n\Idle) And EntityDistanceSquared(n\Collider, me\Collider) < PowTwo(HideDistance * 3.0) Then
					If n\PrevState = 0 Then
						If n\State = 0.0 Lor n\State = 2.0 Then
							For n2.NPCs = Each NPCs
								If n2\NPCtype = n\NPCtype And n2 <> n Then
									If n2\State <> 0.0 And n2\State <> 2.0 Then
										If n2\PrevState = 0 Then
											n\State = 1.0
											n\State2 = 0.0
											Exit
										EndIf
									EndIf
								EndIf
							Next
						EndIf
					EndIf
					
					Select n\State
						Case 0.0
							;[Block]
							If n\PrevState = 0 Then
								If n\CurrSpeed = 0.0 Then
									If n\Reload = 0.0 Then
										If n\State2 < 500.0 * Rnd(1.0, 3.0) Then
											n\CurrSpeed = 0.0
											n\State2 = n\State2 + fps\FPSFactor[0]
										Else
											If n\CurrSpeed = 0.0 Then n\CurrSpeed = n\CurrSpeed + 0.0001
										EndIf
									Else
										If n\State2 < 1500.0 Then
											n\CurrSpeed = 0.0
											n\State2 = n\State2 + fps\FPSFactor[0]
										Else
											If n\Target <> Null Then
												If n\Target\Target <> Null Then
													n\Target\Target = Null
												EndIf
												n\Target\Reload = 0.0
												n\Target\Angle = n\Target\Angle + Rnd(-45.0, 45.0)
												n\Target = Null
												n\Reload = 0.0
												n\Angle = n\Angle + Rnd(-45.0, 45.0)
											EndIf
											If n\CurrSpeed = 0.0 Then n\CurrSpeed = n\CurrSpeed + 0.0001
										EndIf
									EndIf
								Else
									If n\Target <> Null Then
										n\State2 = 0.0
									EndIf
									
									If n\State2 < 10000.0 * Rnd(1.0, 3.0)
										n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 10.0)
										n\State2 = n\State2 + fps\FPSFactor[0]
									Else
										n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 50.0)
									EndIf
									
									RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle, EntityYaw(n\Collider), 10.0), 0.0)
									
									If n\Target = Null Then
										If Rand(200) = 1 Then n\Angle = n\Angle + Rnd(-45.0, 45.0)
										
										HideEntity(n\Collider)
										EntityPick(n\Collider, 1.5)
										If PickedEntity() <> 0 Then
											n\Angle = EntityYaw(n\Collider) + Rnd(80.0, 110.0)
										EndIf
										ShowEntity(n\Collider)
									Else
										n\Angle = EntityYaw(n\Collider) + DeltaYaw(n\Collider, n\Target\Collider)
										If EntityDistanceSquared(n\Collider, n\Target\Collider) < 2.25 Then
											n\CurrSpeed = 0.0
											n\Target\CurrSpeed = 0.0
											n\Reload = 1.0
											n\Target\Reload = 1.0
											Temp = Rand(0, 2)
											If Temp = 0 Then
												SetNPCFrame(n, 296.0)
											ElseIf Temp = 1 Then
												SetNPCFrame(n, 856.0)
											Else
												SetNPCFrame(n, 905.0)
											EndIf
											Temp = Rand(0, 2)
											If Temp = 0 Then
												SetNPCFrame(n\Target, 296.0)
											ElseIf Temp = 1 Then
												SetNPCFrame(n\Target, 856.0)
											Else
												SetNPCFrame(n\Target, 905.0)
											EndIf
										EndIf
									EndIf
								EndIf
							Else
								RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle, EntityYaw(n\Collider), 10.0), 0.0)
							EndIf
							
							If n\CurrSpeed = 0.0
								If n\Reload = 0.0 And n\PrevState <> 2 Then
									AnimateNPC(n, 296.0, 320.0, 0.2)
								ElseIf n\Reload = 0.0 And n\PrevState = 2 Then
									If n\Frame =< 532.5 Then
										AnimateNPC(n, 509.0, 533.0, 0.2, False)
									ElseIf n\Frame > 533.5 And n\Frame =< 600.5 Then
										AnimateNPC(n, 534.0, 601.0, 0.2, False)
									Else
										Temp = Rand(0, 1)
										If Temp = 0 Then
											SetNPCFrame(n, 509.0)
										Else
											SetNPCFrame(n, 534.0)
										EndIf
									EndIf
								Else
									If n\PrevState = 2 Then
										AnimateNPC(n, 713.0, 855.0, 0.2, False)
										If n\Frame > 833.5 Then
											PointEntity(n\OBJ, me\Collider)
											RotateEntity(n\Collider, 0.0, CurveAngle(n\Angle, EntityYaw(n\Collider), 10.0), 0.0)
										EndIf
									ElseIf n\PrevState = 1 Then
										AnimateNPC(n, 602.0, 712.0, 0.2, False)
										If n\Frame > 711.5 Then
											n\Reload = 0.0
										EndIf
									Else
										If n\Frame =< 319.5 Then
											AnimateNPC(n, 296.0, 320.0, 0.2, False)
										ElseIf n\Frame > 320.5 And  n\Frame < 903.5 Then
											AnimateNPC(n, 856.0, 904.0, 0.2, False)
										ElseIf n\Frame > 904.5 And n\Frame < 952.5 Then
											AnimateNPC(n, 905.0, 953.0, 0.2, False)
										Else
											Temp = Rand(0, 2)
											If Temp = 0 Then
												SetNPCFrame(n, 296.0)
											ElseIf Temp = 1 Then
												SetNPCFrame(n, 856.0)
											Else
												SetNPCFrame(n, 905.0)
											EndIf
										EndIf
									EndIf
								EndIf
							Else
								If (n\ID Mod 2) = 0 Then
									AnimateNPC(n, 1.0, 62.0, n\CurrSpeed * 28.0)
								Else
									AnimateNPC(n, 100.0, 167.0, n\CurrSpeed * 28.0)
								EndIf
								If n\PrevState = 0 Then
									If n\Target = Null Then
										If Rand(1, 1200) = 1 Then
											For n2.NPCs = Each NPCs
												If n2 <> n Then
													If n2\NPCtype = n\NPCtype Then
														If n2\Target = Null Then
															If n2\PrevState = 0 Then
																If EntityDistanceSquared(n\Collider, n2\Collider) < 400.0 Then
																	n\Target = n2
																	n2\Target = n
																	n\Target\CurrSpeed = n\Target\CurrSpeed + 0.0001
																	Exit
																EndIf
															EndIf
														EndIf
													EndIf
												EndIf
											Next
										EndIf
									EndIf
								EndIf
							EndIf
							
							; ~ Randomly play the "screaming animation" and revert back to State = 0.0
							If n\Target = Null And n\PrevState = 0 Then
								If Rand(5000) = 1 Then
									n\State = 2.0
									n\State2 = 0.0
									
									If (Not ChannelPlaying(n\SoundCHN)) Then
										Dist = EntityDistanceSquared(n\Collider, me\Collider)
										If Dist < 400.0 Then
											If n\Sound <> 0 Then 
												FreeSound_Strict(n\Sound) : n\Sound = 0
											EndIf
											n\Sound = LoadSound_Strict("SFX\SCP\1499\Idle" + Rand(1, 4) + ".ogg")
											n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider, 20.0)
										EndIf
									EndIf
								EndIf
								
								If (n\ID Mod 2) = 0 And (Not chs\NoTarget) Then
									Dist = EntityDistanceSquared(n\Collider, me\Collider)
									If Dist < 100.0 Then
										If EntityVisible(n\Collider, me\Collider) Then
											; ~ Play the "screaming animation"
											n\State = 2.0
											If Dist < 25.0 Then
												If n\Sound <> 0 Then 
													FreeSound_Strict(n\Sound) : n\Sound = 0
												EndIf
												n\Sound = LoadSound_Strict("SFX\SCP\1499\Triggered.ogg")
												n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider, 20.0)
												
												n\State2 = 1.0 ; ~ If player is too close, switch to attack after screaming
												
												For n2.NPCs = Each NPCs
													If n2\NPCtype = n\NPCtype And n2 <> n
														If n2\PrevState = 0 Then
															n2\State = 1.0
															n2\State2 = 0.0
														EndIf
													EndIf
												Next
											Else
												n\State2 = 0.0 ; ~ Otherwise keep idling
											EndIf
											SetNPCFrame(n, 203.0)
										EndIf
									EndIf
								EndIf
							ElseIf n\PrevState = 1
								Dist = EntityDistanceSquared(n\Collider, me\Collider)
								If (Not chs\NoTarget) Then
									If Dist < 16.0 Then
										If EntityVisible(n\Collider, me\Collider) Then
											If n\Sound <> 0 Then 
												FreeSound_Strict(n\Sound) : n\Sound = 0
											EndIf
											n\Sound = LoadSound_Strict("SFX\SCP\1499\Triggered.ogg")
											n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider, 20.0)
											
											n\State = 1.0
											
											SetNPCFrame(n, 203.0)
										EndIf
									EndIf
								EndIf
							EndIf
							;[End Block]
						Case 1.0 ; ~ Attacking the player
							;[Block]
							If chs\NoTarget Then n\State = 0.0
							
							If PlayerRoom\RoomTemplate\Name = "dimension1499" And n\PrevState = 0 Then
								ShouldPlay = 19
							EndIf
							
							PointEntity(n\OBJ, me\Collider)
							RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
							
							Dist = EntityDistanceSquared(n\Collider, me\Collider)
							
							If n\State2 = 0.0
								If n\PrevState = 1 Then
									n\CurrSpeed = CurveValue(n\Speed * 2.0, n\CurrSpeed, 10.0)
									If n\Target <> Null Then
										n\Target\State = 1.0
									EndIf
								Else
									n\CurrSpeed = CurveValue(n\Speed * 1.75, n\CurrSpeed, 10.0)
								EndIf
								
								If (n\ID Mod 2) = 0 Then
									AnimateNPC(n, 1.0, 62.0, n\CurrSpeed * 28.0)
								Else
									AnimateNPC(n, 100.0, 167.0, n\CurrSpeed * 28.0)
								EndIf
							EndIf
							
							If Dist < 0.5625 Then
								If (n\ID Mod 2) = 0 Lor n\State3 = 1.0 Lor n\PrevState = 1 Lor n\PrevState = 3 Lor n\PrevState = 4 Then
									n\State2 = Rand(1.0, 2.0)
									n\State = 3.0
									If n\State2 = 1.0
										SetNPCFrame(n, 63.0)
									Else
										SetNPCFrame(n, 168.0)
									EndIf
								Else
									n\State = 4.0
								EndIf
							EndIf
							;[End Block]
						Case 2.0 ; ~ Play the "screaming animation" and switch to n\State2 after it's finished
							;[Block]
							n\CurrSpeed = 0.0
							AnimateNPC(n, 203.0, 295.0, 0.1, False)
							
							If n\Frame > 294.0 Then
								n\State = n\State2
							EndIf
							;[End Block]
						Case 3.0 ; ~ Slashing at the player
							;[Block]
							n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 5.0)
							Dist = EntityDistanceSquared(n\Collider, me\Collider)
							If n\State2 = 1.0
								AnimateNPC(n, 63.0, 100.0, 0.6, False)
								If PrevFrame < 89.0 And n\Frame >= 89.0 Then
									If Dist > 0.7225 Lor Abs(DeltaYaw(n\Collider, me\Collider)) > 60.0 Then
										PlaySound2(MissSFX, Camera, n\Collider, 2.5)
									Else
										InjurePlayer(Rnd(0.75, 1.5), 0.0, 500.0, Rnd(0.1, 0.4), 0.2)
										PlaySound2(DamageSFX[Rand(11, 12)], Camera, n\Collider)
										If me\Injuries > 10.0 Then
											Kill(True)
											If PlayerRoom\RoomTemplate\Name = "dimension1499"
												msg\DeathMsg = "All personnel situated within Evacuation Shelter LC-2 during the breach have been administered "
												msg\DeathMsg = msg\DeathMsg + "Class-B amnestics due to Incident 1499-E. The Class D subject involved in the event "
												msg\DeathMsg = msg\DeathMsg + "died shortly after being shot by Agent [DATA REDACTED]."
											Else
												If Rand(2) = 1 Then
													GroupDesignation = "Nine-Tailed Fox"
												Else
													GroupDesignation = "See No Evil"
												EndIf
												msg\DeathMsg = "An unidentified male and a deceased Class D subject were discovered in [DATA REDACTED] by the " + GroupDesignation + ". "
												msg\DeathMsg = msg\DeathMsg + "The man was described as highly agitated and seemed to only speak Russian. "
												msg\DeathMsg = msg\DeathMsg + "He's been taken into a temporary holding area at [DATA REDACTED] while waiting for a translator to arrive."
											EndIf
										EndIf
									EndIf
								ElseIf n\Frame >= 99.0
									n\State2 = 0.0
									n\State = 1.0
								EndIf
							Else
								AnimateNPC(n, 168.0, 202.0, 0.6, False)
								If PrevFrame < 189.0 And n\Frame >= 189.0 Then
									If Dist > 0.7225 Lor Abs(DeltaYaw(n\Collider, me\Collider)) > 60.0 Then
										PlaySound2(MissSFX, Camera, n\Collider, 2.5)
									Else
										InjurePlayer(Rnd(0.75, 1.5), 0.0, 500.0, Rnd(0.1, 0.4), 0.2)
										PlaySound2(DamageSFX[Rand(11, 12)], Camera, n\Collider)
										If me\Injuries > 10.0 Then
											Kill(True)
											If PlayerRoom\RoomTemplate\Name = "dimension1499"
												msg\DeathMsg = "All personnel situated within Evacuation Shelter LC-2 during the breach have been administered "
												msg\DeathMsg = msg\DeathMsg + "Class-B amnestics due to Incident 1499-E. The Class D subject involved in the event "
												msg\DeathMsg = msg\DeathMsg + "died shortly after being shot by Agent [DATA REDACTED]."
											Else
												If Rand(2) = 1 Then
													GroupDesignation = "Nine-Tailed Fox"
												Else
													GroupDesignation = "See No Evil"
												EndIf
												msg\DeathMsg = "An unidentified male and a deceased Class D subject were discovered in [DATA REDACTED] by the " + GroupDesignation + ". "
												msg\DeathMsg = msg\DeathMsg + "The man was described as highly agitated and seemed to only speak Russian. "
												msg\DeathMsg = msg\DeathMsg + "He's been taken into a temporary holding area at [DATA REDACTED] while waiting for a translator to arrive."
											EndIf
										EndIf
									EndIf
								ElseIf n\Frame >= 201.0
									n\State2 = 0.0
									n\State = 1.0
								EndIf
							EndIf
							;[End Block]
						Case 4.0 ; ~ Standing in front of the player
							;[Block]
							Dist = EntityDistanceSquared(n\Collider, me\Collider)
							n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 5.0)
							AnimateNPC(n, 296.0, 320.0, 0.2)
							
							PointEntity(n\OBJ, me\Collider)
							RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
							
							If Dist > 0.7225 Then n\State = 1.0
							;[End Block]
					End Select
					
					If n\SoundCHN <> 0 And ChannelPlaying(n\SoundCHN) = True Then
						UpdateSoundOrigin(n\SoundCHN, Camera, n\Collider, 20.0)
					EndIf
					
					MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
					
					RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider) - 180.0, 0.0)
					PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.2, EntityZ(n\Collider))
					
					ShowEntity(n\OBJ)
				Else
					HideEntity(n\OBJ)
				EndIf
				;[End Block]
			Case NPCtype008_1
				;[Block]
				; ~ n\State: Main State
				
				; ~ n\State2: A timer used for the player detection
				
				; ~ n\State3: A timer for making the NPC idle (if the player escapes during that time)
				
				If (Not n\IsDead) Then
					Dist = EntityDistanceSquared(n\Collider, me\Collider)
					
					PrevFrame = n\Frame
					
					n\BlinkTimer = 1.0
					
					PlayerSeeAble = MeNPCSeesPlayer(n)
					
					Select n\State
						Case 0.0 ; ~ Lying next to the wall
							;[Block]
							SetNPCFrame(n, 11.0)
							;[End Block]
						Case 1.0 ; ~ Stands up
							;[Block]
							AnimateNPC(n, 11.0, 29.0, 0.1, False)
							If n\Frame = 29.0
								n\State = 2.0
							EndIf
							;[End Block]
						Case 2.0 ; ~ Player is visible, tries to kill
							;[Block]
							If PlayerSeeAble = True Lor n\State2 > 0.0 And (Not chs\Notarget) Then
								If PlayerSeeAble = True
									n\State2 = 70.0 * 2.0
								Else
									n\State2 = Max(n\State2 - fps\FPSFactor[0], 0.0)
								EndIf
								PointEntity(n\OBJ, me\Collider)
								RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
								
								AnimateNPC(n, 64.0, 93.0, n\CurrSpeed * 30.0)
								n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								
								If Dist < 1.21 Then
									If Abs(DeltaYaw(n\Collider, me\Collider)) =< 60.0 Then
										n\State = 4.0
									EndIf
								EndIf
								
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\PathLocation = 0
							Else
								n\State = 3.0
							EndIf
							;[End Block]
						Case 3.0 ; ~ Player isn't visible, tries to find
							;[Block]
							If PlayerSeeAble = True And (Not chs\Notarget) Then
								n\State = 2.0
							EndIf
							
							If n\PathStatus = 1
								If n\Path[n\PathLocation] = Null Then 
									If n\PathLocation > 19 Then 
										n\PathLocation = 0 : n\PathStatus = 0
									Else
										n\PathLocation = n\PathLocation + 1
									EndIf
								Else
									PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
									RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
									
									AnimateNPC(n, 64.0, 93.0, n\CurrSpeed * 30.0)
									n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
									MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
									
									; ~ Opens doors in front of him
									Dist2 = EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ)
									If Dist2 < 0.36 Then
										Temp = True
										If n\Path[n\PathLocation]\door <> Null
											If (Not n\Path[n\PathLocation]\door\IsElevatorDoor)
												If ((n\Path[n\PathLocation]\door\Locked = 1 Lor n\Path[n\PathLocation]\door\KeyCard > 0 Lor n\Path[n\PathLocation]\door\Code <> "") And (Not n\Path[n\PathLocation]\door\Open)) Then
													Temp = False
												Else
													If (Not n\Path[n\PathLocation]\door\Open) Then UseDoor(n\Path[n\PathLocation]\Door, False)
												EndIf
											EndIf
											If n\Path[n\PathLocation]\door\OpenState >= 180.0
												n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
											Else
												n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
											EndIf
										Else
											n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
										EndIf
										If Dist2 < 0.04 And Temp Then
											n\PathLocation = n\PathLocation + 1
										ElseIf Dist2 < 0.25 And (Not Temp)
											n\PathStatus = 0
											n\PathTimer = 0.0
										EndIf
									Else
										n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
									EndIf
								EndIf
							Else
								; ~ No path, stands still
								If n\CurrSpeed =< 0.001 Then
									AnimateNPC(n, 323.0, 344.0, 0.2)
									n\CurrSpeed = 0.0
									
									n\PathTimer = n\PathTimer - fps\FPSFactor[0]
									If n\PathTimer < 70.0 * 5.0
										n\PathTimer = n\PathTimer + Rnd(1.0, 2.0 + (2.0 * SelectedDifficulty\AggressiveNPCs)) * fps\FPSFactor[0]
									Else
										n\EnemyX = 0.0
										n\EnemyY = 0.0
										n\EnemyZ = 0.0
										
										; ~ When stands still, tries to find a room that is close
										For r.Rooms = Each Rooms
											If EntityDistanceSquared(r\OBJ, n\Collider) < 196.0 And EntityDistanceSquared(r\OBJ, n\Collider) > 36.0 Then
												n\EnemyX = EntityX(r\OBJ)
												n\EnemyY = EntityY(r\OBJ)
												n\EnemyZ = EntityZ(r\OBJ)
												Exit
											EndIf
										Next
										
										; ~ The room wasn't found, tries to get the coordinates from a random waypoint
										If n\EnemyX = 0.0 And n\EnemyY = 0.0 And n\EnemyZ = 0.0 Then
											For w.WayPoints = Each WayPoints
												If EntityDistanceSquared(w\OBJ, n\Collider) < 100.0 And EntityDistanceSquared(w\OBJ, n\Collider) > 16.0 Then
													n\EnemyX = EntityX(w\OBJ)
													n\EnemyY = EntityY(w\OBJ)
													n\EnemyZ = EntityZ(w\OBJ)
													Exit
												EndIf
											Next
										EndIf
										
										; ~ Just tries to find a path, if possible
										If n\EnemyX <> 0.0 Lor n\EnemyY <> 0.0 Lor n\EnemyZ <> 0.0 Then
											n\PathStatus = FindPath(n, n\EnemyX, n\EnemyY, n\EnemyZ)
										EndIf
										n\PathTimer = 0.0
									EndIf
								Else
									n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
									AnimateNPC(n, 64.0, 93.0, n\CurrSpeed * 30.0)
									MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								EndIf
								
								If EntityDistanceSquared(n\Collider, me\Collider) > PowTwo(HideDistance)
									If n\State3 < 70.0 * (15.0 + (10.0 * SelectedDifficulty\AggressiveNPCs))
										n\State3 = n\State3 + fps\FPSFactor[0]
									Else
										n\State3 = 70.0 * (6.0 * 60.0)
										n\State = 5.0
									EndIf
								EndIf
							EndIf
							;[End Block]
						Case 4.0 ; ~ Attacks
							;[Block]
							AnimateNPC(n, 126.0, 165.0, 0.6, False)
							If (n\Frame >= 146.0 And PrevFrame < 146.0)
								If Dist < 1.21 Then
									If Abs(DeltaYaw(n\Collider, me\Collider)) =< 60.0 Then
										PlaySound_Strict(DamageSFX[Rand(5, 8)])
										InjurePlayer(Rnd(0.4, 1.0), 1.0 + (1.0 * SelectedDifficulty\AggressiveNPCs), 0.0, Rnd(0.1, 0.25), 0.2)
										If me\Injuries > 3.0 Then
											If Rand(2) = 1 Then
												GroupDesignation = "Nine-Tailed Fox"
											Else
												GroupDesignation = "See No Evil"
											EndIf
											msg\DeathMsg = SubjectName + ". Cause of death: multiple lacerations and severe blunt force trauma caused by [DATA REDACTED], who was infected with SCP-008. Said subject was located by " + GroupDesignation + " and terminated."
											Kill(True)
										EndIf
									Else
										PlaySound2(MissSFX, Camera, n\Collider)
									EndIf
								Else
									PlaySound2(MissSFX, Camera, n\Collider, 2.5)
								EndIf
							ElseIf n\Frame >= 164.0
								If EntityDistanceSquared(n\Collider, me\Collider) < 1.21
									If (Abs(DeltaYaw(n\Collider, me\Collider)) =< 60.0)
										SetNPCFrame(n, 126.0)
									Else
										n\State = 2.0
									EndIf
								Else
									n\State = 2.0
								EndIf
							EndIf
							;[End Block]
						Case 5.0 ; ~ Idling
							;[Block]
							HideEntity(n\OBJ)
							HideEntity(n\Collider)
							n\DropSpeed = 0.0
							PositionEntity(n\Collider, 0.0, 500.0, 0.0)
							ResetEntity(n\Collider)
							If n\Idle > 0
								n\Idle = Max(n\Idle - (1 + (1 * SelectedDifficulty\AggressiveNPCs)) * fps\FPSFactor[0], 0.0)
							Else
								If PlayerInReachableRoom() ; ~ Player is in a room where SCP-008-1 can teleport to
									If Rand(50 - (20 * SelectedDifficulty\AggressiveNPCs)) = 1
										ShowEntity(n\Collider)
										ShowEntity(n\OBJ)
										For w.Waypoints = Each WayPoints
											If w\door = Null And w\room\Dist < HideDistance And Rand(3) = 1 Then
												If EntityDistanceSquared(w\room\OBJ, n\Collider) < EntityDistanceSquared(me\Collider, n\Collider)
													x = Abs(EntityX(n\Collider) - EntityX(w\OBJ, True))
													If x < 12.0 And x > 4.0 Then
														z = Abs(EntityZ(n\Collider) - EntityZ(w\OBJ, True))
														If z < 12.0 And z > 4.0 Then
															If w\room\Dist > 4.0
																PositionEntity(n\Collider, EntityX(w\OBJ, True), EntityY(w\obj, True) + 0.25, EntityZ(w\OBJ, True))
																ResetEntity(n\Collider)
																n\PathStatus = 0
																n\PathTimer = 0.0
																n\PathLocation = 0
																Exit
															EndIf
														EndIf
													EndIf
												EndIf
											EndIf
										Next
										n\State = 2.0
										n\State3 = 0.0
									EndIf
								EndIf
							EndIf
							;[End Block]
					End Select
					
					; ~ Loop the walk sound
					If n\CurrSpeed > 0.005 Then
						If (PrevFrame < 80.0 And n\Frame >= 80.0) Lor (PrevFrame > 92.0 And n\Frame < 65.0)
							PlaySound2(StepSFX(GetStepSound(n\Collider), 0, Rand(0, 7)), Camera, n\Collider, 8.0, Rnd(0.3, 0.5))
						EndIf
					EndIf
					
					; ~ Loop the breath sound
					If n\State > 1.0 And n\State < 5.0 Then
						n\SoundCHN = LoopSound2(n\Sound, n\SoundCHN, Camera, n\Collider)
					EndIf
				Else
					; ~ The NPC was killed
					If n\SoundCHN <> 0 Then
						If ChannelPlaying(n\SoundCHN) Then StopChannel(n\SoundCHN)
						If n\Sound <> 0 Then
							FreeSound_Strict(n\Sound) : n\Sound = 0
						EndIf
					EndIf
					AnimateNPC(n, 344.0, 363.0, 0.5, False)
				EndIf
				
				RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider) - 180.0, 0.0)
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.2, EntityZ(n\Collider))
				;[End Block]
			Case NPCtypeVehicle 
				;[Block]
				Select n\State
					Case 0.0 ; ~ Idles
						;[Block]
					    n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 5.0)
						;[End Block]
					Case 1.0 ; ~ Moves
						;[Block]
						n\CurrSpeed = CurveValue(n\Speed * 0.7, n\CurrSpeed, 20.0)
						Animate2(n\OBJ, AnimTime(n\OBJ), 1.0, 20.0, n\CurrSpeed * 18.0)
						;[End Block]
				End Select
				
				If n\State = 0.0 Then
					If (Not n\Sound) Then
						n\Sound = LoadSound_Strict("SFX\Character\Vehicle\Idle.ogg")
					EndIf
					If n\Sound2 <> 0 Then
						FreeSound_Strict(n\Sound2) : n\Sound2 = 0
					EndIf
					n\SoundCHN = LoopSound2(n\Sound, n\SoundCHN, Camera, n\Collider, 11.5, 1.0)
				Else
					If (Not n\Sound2) Then
						n\Sound2 = LoadSound_Strict("SFX\Character\Vehicle\Move.ogg")
					EndIf
					If n\Sound <> 0 Then
						FreeSound_Strict(n\Sound) : n\Sound = 0
					EndIf
					n\SoundCHN2 = LoopSound2(n\Sound2, n\SoundCHN2, Camera, n\Collider, 11.5, 1.0)
				EndIf
				
				MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
				
				PositionEntity(n\OBJ, EntityX(n\Collider), EntityY(n\Collider) - 0.32, EntityZ(n\Collider))
				
				RotateEntity(n\OBJ, 0.0, EntityYaw(n\Collider), 0.0)
				;[End Block]
		End Select
		
		If n\IsDead Then EntityType(n\Collider, HIT_DEAD)
		
		Local GravityDist# = Distance(EntityX(me\Collider), EntityX(n\Collider), EntityZ(me\Collider), EntityZ(n\Collider))
		
		If GravityDist < HideDistance * 0.7 Lor n\NPCtype = NPCtype1499_1 Then
			If n\InFacility = InFacility Then
				TranslateEntity(n\Collider, 0.0, n\DropSpeed, 0.0)
				
				Local CollidedFloor% = False
				
				For i = 1 To CountCollisions(n\Collider)
					If CollisionY(n\Collider, i) < EntityY(n\Collider) - 0.01 Then CollidedFloor = True : Exit
				Next
				
				If CollidedFloor Then
					n\DropSpeed = 0.0
				Else
					If ShouldEntitiesFall Then
						Local UpdateGravity% = False
						Local MaxX#, MinX#, MaxZ#, MinZ#
						
						If n\InFacility = 1 Then
							If PlayerRoom\RoomTemplate\Name <> "room173intro" Then
								If PlayerRoom\RoomTemplate\Name = "room860" Then
									For e.Events = Each Events
										If e\EventID = e_room860 Then
											If e\EventState = 1.0
												UpdateGravity = True
											EndIf
											Exit
										EndIf
									Next
								EndIf
							Else
								UpdateGravity = True
							EndIf
							If (Not UpdateGravity) Then
								For r.Rooms = Each Rooms
									If r\MaxX <> 0.0 Lor r\MinX <> 0.0 Lor r\MaxZ <> 0.0 Lor r\MinZ <> 0.0
										MaxX = r\MaxX
										MinX = r\MinX
										MaxZ = r\MaxZ
										MinZ = r\MinZ
									Else
										MaxX = 4.0
										MinX = 0.0
										MaxZ = 4.0
										MinZ = 0.0
									EndIf
									If Abs(EntityX(n\Collider) - EntityX(r\OBJ)) =< Abs(MaxX - MinX)
										If Abs(EntityZ(n\Collider) - EntityZ(r\OBJ)) =< Abs(MaxZ - MinZ)
											If r = PlayerRoom Then
												UpdateGravity = True
												Exit
											EndIf
											If IsRoomAdjacent(PlayerRoom, r) Then
												UpdateGravity = True
												Exit
											EndIf
											For i = 0 To 3
												If IsRoomAdjacent(PlayerRoom\Adjacent[i], r) Then
													UpdateGravity = True
													Exit
												EndIf
											Next
										EndIf
									EndIf
								Next
							EndIf
						Else
							UpdateGravity = True
						EndIf
						If UpdateGravity
							n\DropSpeed = Max(n\DropSpeed - 0.005 * fps\FPSFactor[0] * n\GravityMult, -n\MaxGravity)
						Else
							If n\FallingPickDistance > 0.0
								n\DropSpeed = 0.0
							Else
								n\DropSpeed = Max(n\DropSpeed - 0.005 * fps\FPSFactor[0] * n\GravityMult, -n\MaxGravity)
							EndIf
						EndIf
					Else
						n\DropSpeed = 0.0
					EndIf
				EndIf
			Else
				n\DropSpeed = 0.0
			EndIf
		Else
			n\DropSpeed = 0.0
		EndIf
		CatchErrors(Chr(34) + n\NVName + Chr(34) + " NPC")
	Next
	
	UpdateCameraCheck()
End Function

Function TeleportCloser(n.NPCs)
	Local ClosestDist# = 0.0
	Local closestWaypoint.WayPoints
	Local w.WayPoints
	
	Local xTemp#, zTemp#
	
	For w.WayPoints = Each WayPoints
		If w\door = Null Then
			xTemp = Abs(EntityX(w\OBJ, True) - EntityX(n\Collider, True))
			If xTemp < 10.0 And xTemp > 1.0 Then 
				zTemp = Abs(EntityZ(w\OBJ, True) - EntityZ(n\Collider, True))
				If zTemp < 10.0 And zTemp > 1.0 Then
					If EntityDistanceSquared(me\Collider, w\OBJ) > PowTwo(16.0 - (8.0 * SelectedDifficulty\AggressiveNPCs)) Then
						; ~ Teleports to the nearby waypoint that takes it closest to the player
						Local NewDist# = EntityDistance(me\Collider, w\OBJ)
						
						If NewDist < ClosestDist Lor closestWaypoint = Null Then
							ClosestDist = NewDist	
							closestWaypoint = w
						EndIf						
					EndIf
				EndIf
			EndIf
		EndIf
	Next
	
	Local ShouldTeleport% = False
	
	If (closestWaypoint <> Null) Then
		If n\InFacility <> 1 Lor SelectedDifficulty\AggressiveNPCs Then
			ShouldTeleport = True
		ElseIf EntityY(closestWaypoint\OBJ, True) =< 7.0 And EntityY(closestWaypoint\OBJ, True) >= -10.0 Then
			ShouldTeleport = True
		EndIf
		
		If ShouldTeleport Then
			PositionEntity(n\Collider, EntityX(closestWaypoint\OBJ, True), EntityY(closestWaypoint\OBJ, True) + 0.15, EntityZ(closestWaypoint\OBJ, True), True)
			ResetEntity(n\Collider)
			n\PathStatus = 0
			n\PathTimer = 0.0
			n\PathLocation = 0
		EndIf
	EndIf
End Function

Function OtherNPCSeesMeNPC%(n.NPCs, n2.NPCs)
	If n2\BlinkTimer =< 0.0 Then Return(False)
	
	If EntityDistanceSquared(n2\Collider, n\Collider) < 36.0 Then
		If Abs(DeltaYaw(n2\Collider, n\Collider)) < 60.0 Then
			Return(True)
		EndIf
	EndIf
	Return(False)
End Function

Function MeNPCSeesPlayer%(n.NPCs, DisableSoundOnCrouch% = False)
	; ~ Return values:
	; ~ False (= 0): Player is Not detected anyhow
	; ~ True (= 1): Player is detected by vision
	; ~ 2: Player is detected by emitting a sound
	; ~ 3: Player is detected by a camera (only for MTF Units!)
	; ~ 4: Player is detected through glass
	
	If chs\NoTarget Then Return(False)
	
	If (Not me\Detected) Lor n\NPCtype <> NPCtypeMTF
		If n\BlinkTimer =< 0.0 Then Return(False)
		If EntityDistanceSquared(me\Collider, n\Collider) > PowTwo(8.0 - me\CrouchState + me\SndVolume) Then Return(False)
		
		; ~ Spots the player if he's either in view or making a loud sound
		If me\SndVolume > 1.0
			If (Abs(DeltaYaw(n\Collider, me\Collider)) > 60.0) And EntityVisible(n\Collider, me\Collider)
				Return(1)
			ElseIf (Not EntityVisible(n\Collider, me\Collider))
				If DisableSoundOnCrouch And me\Crouch Then
					Return(False)
				Else
					Return(2)
				EndIf
			EndIf
		Else
			If Abs(DeltaYaw(n\Collider, me\Collider)) > 60.0 Then Return(False)
		EndIf
		Return(EntityVisible(n\Collider, me\Collider))
	Else
		If EntityDistanceSquared(me\Collider, n\Collider) > PowTwo(8.0 - me\CrouchState + me\SndVolume) Then Return(3)
		If EntityVisible(n\Collider, Camera) Then Return(True)
		
		; ~ Spots the player if he's either in view or making a loud sound
		If me\SndVolume > 1.0 Then Return(2)
		Return(3)
	EndIf
End Function

Function UpdateMTFUnit(n.NPCs)
	Local x#, y#, z#
	Local r.Rooms, p.Particles, wp.Waypoints
	Local PrevDist#, NewDist#
	Local n2.NPCs
	Local Target%, Dist#, Dist2#
	
	If n\IsDead Then
		n\BlinkTimer = -1.0
		SetNPCFrame(n, 532.0)
		If ChannelPlaying(n\SoundCHN2) = True Then
			StopChannel(n\SoundCHN2)
		EndIf
		Return
	EndIf
	
	n\MaxGravity = 0.03
	
	n\BlinkTimer = n\BlinkTimer - fps\FPSFactor[0]
	If n\BlinkTimer =< -5.0 Then 
		; ~ Only play the "blinking" sound clip if searching / containing SCP-173
		If n\State = 2.0
			If EntityDistanceSquared(Curr173\Collider, n\Collider) < 64.0 Then
				PlayMTFSound(LoadTempSound("SFX\Character\MTF\173\BLINKING.ogg"), n)
			EndIf
		EndIf
		n\BlinkTimer = 70.0 * Rnd(6.0, 7.0)
	EndIf	
	
	n\Reload = n\Reload - fps\FPSFactor[0]
	
	Local PrevFrame# = n\Frame
	
	If n\State <> 1.0 Then n\PrevState = 0
	
	n\SoundCHN2 = LoopSound2(MTFSFX[1], n\SoundCHN2, Camera, n\Collider)
	
	If n\Idle > 0.0 Then
		FinishWalking(n, 488.0, 522.0, 0.015 * 26.0)
		n\Idle = n\Idle - fps\FPSFactor[0]
		If n\Idle =< 0.0 Then n\Idle = 0.0
	Else
		Select n\State
			Case 0.0 ; ~ Wandering around
                ;[Block]
                n\Speed = 0.015
                If n\PathTimer =< 0.0 Then
					If n\MTFLeader <> Null Then
						n\PathStatus = FindPath(n, EntityX(n\MTFLeader\Collider, True), EntityY(n\MTFLeader\Collider, True) + 0.1, EntityZ(n\MTFLeader\Collider, True))
					Else
						If Curr173\Idle<>2
							For r = Each Rooms
								If ((Abs(r\x - EntityX(n\Collider, True)) > 12.0) Lor (Abs(r\z - EntityZ(n\Collider, True)) > 12.0)) And (Rand(1, Max(4 - Int(Abs(r\z - EntityZ(n\Collider, True) / 8.0)), 2)) = 1) Then
									x = r\x
									y = 0.1
									z = r\z
									Exit
								EndIf
							Next
						Else
							Local Tmp% = False
							
							If EntityDistanceSquared(n\Collider, Curr173\Collider) > 16.0
								If (Not EntityVisible(n\Collider, Curr173\Collider))
									Tmp = True
								EndIf
							EndIf
							
							If (Not Tmp) Then
								For r = Each Rooms
									If r\RoomTemplate\Name = "room173"
										Local FoundChamber% = False
										Local Pvt% = CreatePivot()
										
										PositionEntity(Pvt, EntityX(r\OBJ, True) + 4736.0 * RoomScale, 0.5, EntityZ(r\OBJ, True) + 1692.0 * RoomScale)
										
										If DistanceSquared(EntityX(Pvt), EntityX(n\Collider), EntityZ(Pvt), EntityZ(n\Collider)) < 12.25 Then
											FoundChamber = True
										EndIf
										
										If Curr173\Idle = 3 And DistanceSquared(EntityX(Pvt), EntityX(n\Collider), EntityZ(Pvt), EntityZ(n\Collider)) > 16.0
											If r\RoomDoors[1]\Open = True Then UseDoor(r\RoomDoors[1], False)
										EndIf
										
										FreeEntity(Pvt)
										
										If DistanceSquared(EntityX(n\Collider), EntityX(r\OBJ, True) + 4736.0 * RoomScale, EntityZ(n\Collider), EntityZ(r\OBJ, True) + 1692.0 * RoomScale) > 2.56 And (Not FoundChamber) Then
											x = EntityX(r\OBJ, True) + 4736.0 * RoomScale
											y = 0.1
											z = EntityZ(r\OBJ, True) + 1692.0 * RoomScale
											Exit
										ElseIf DistanceSquared(EntityX(n\Collider), EntityX(r\OBJ, True) + 4736.0 * RoomScale, EntityZ(n\Collider), EntityZ(r\OBJ, True) + 1692.0 * RoomScale) > 2.56 And FoundChamber
											n\PathX = EntityX(r\OBJ, True) + 4736.0 * RoomScale
											n\PathZ = EntityZ(r\OBJ, True) + 1692.0 * RoomScale
											Exit
										Else
											Curr173\Idle = 3
											Curr173\Target = Null
											Curr173\IsDead = True
											If n\Sound <> 0 Then 
												FreeSound_Strict(n\Sound) : n\Sound = 0
											EndIf
											n\Sound = LoadSound_Strict("SFX\Character\MTF\173\Cont" + Rand(1, 4) + ".ogg")
											PlayMTFSound(n\Sound, n)
											PlayAnnouncement("SFX\Character\MTF\Announc173Contain.ogg")
											r\RoomDoors[1]\MTFClose = True
											UseDoor(r\RoomDoors[1], False)
											r\RoomDoors[1]\Open = False
											Exit
										EndIf
									EndIf
								Next
							Else
								x = EntityX(Curr173\Collider)
								y = 0.1
								z = EntityZ(Curr173\Collider)
							EndIf
						EndIf
						If n\PathX = 0 Then n\PathStatus = FindPath(n, x, y, z)
					EndIf
					If n\PathStatus = 1 Then
						While n\Path[n\PathLocation] = Null
							If n\PathLocation > 19 Then Exit
							n\PathLocation = n\PathLocation + 1
						Wend
						If n\PathLocation < 19 Then
							If (n\Path[n\PathLocation] <> Null) And (n\Path[n\PathLocation + 1] <> Null) Then
								If (n\Path[n\PathLocation]\door = Null) Then
									If Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation]\OBJ)) > Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation + 1]\OBJ)) Then
										n\PathLocation = n\PathLocation + 1
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
					n\PathTimer = 70.0 * Rnd(6.0, 10.0)
                ElseIf (n\PathTimer =< 70.0 * 2.5) And (n\MTFLeader = Null) Then
					n\PathTimer = n\PathTimer - fps\FPSFactor[0]
					n\CurrSpeed = 0.0
					If Rand(1, 35) = 1 Then
						RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
					EndIf
					FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
					n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
					RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
                Else
					If n\PathStatus = 2 Then
						n\PathTimer = n\PathTimer - (fps\FPSFactor[0] * 2.0)
						n\CurrSpeed = 0.0
						If Rand(1, 35) = 1 Then
							RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
						EndIf
						FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
					ElseIf n\PathStatus = 1 Then
						If n\Path[n\PathLocation] = Null Then
							If n\PathLocation > 19 Then
								n\PathLocation = 0 : n\PathStatus = 0
							Else
								n\PathLocation = n\PathLocation + 1
							EndIf
						Else
							PrevDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
							
							PointEntity(n\Collider, n\Path[n\PathLocation]\OBJ)
							RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
							
							n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
							
							RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
							
							n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
							
							TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
							AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
							
							NewDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
							
							If NewDist < 1.0 And n\Path[n\PathLocation]\door <> Null Then
								; ~ Open the door and make it automatically close after 5 seconds
								If (Not n\Path[n\PathLocation]\door\Open)
									PlaySound2(OpenDoorSFX(n\Path[n\PathLocation]\door\DoorType, Rand(0, 2)), Camera, n\Path[n\PathLocation]\door\OBJ)
									PlayMTFSound(MTFSFX[0], n)
								EndIf
								n\Path[n\PathLocation]\door\Open = True
								If n\Path[n\PathLocation]\door\MTFClose
									n\Path[n\PathLocation]\door\TimerState = 70.0 * 5.0
								EndIf
							EndIf
                            
							If (NewDist < 0.2) Lor ((PrevDist < NewDist) And (PrevDist < 1.0)) Then
								n\PathLocation = n\PathLocation + 1
							EndIf
						EndIf
						n\PathTimer = n\PathTimer - fps\FPSFactor[0]
					ElseIf n\PathX <> 0.0
						Pvt = CreatePivot()
						PositionEntity(Pvt, n\PathX, 0.5, n\PathZ)
						
						PointEntity(n\Collider, Pvt)
						RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
						
						n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
						TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
						AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
						
						If DistanceSquared(EntityX(n\Collider), n\PathX, EntityZ(n\Collider), n\PathZ) < 0.04 Then
							n\PathX = 0.0
							n\PathZ = 0.0
							n\PathTimer = 70.0 * Rnd(6.0, 10.0)
						EndIf
						
						FreeEntity(Pvt)
					Else
						n\PathTimer = n\PathTimer - (fps\FPSFactor[0] * 2.0)
						If n\MTFLeader = Null Then
							If Rand(1, 35) = 1 Then
								RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
							EndIf
							FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
							n\CurrSpeed = 0.0
						ElseIf EntityDistanceSquared(n\Collider, n\MTFLeader\Collider) > 1.21 Then
							PointEntity(n\Collider, n\MTFLeader\Collider)
							RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
							
							n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
							TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
							AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
						Else
							If Rand(1, 35) = 1 Then
								RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
							EndIf
							FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
							n\CurrSpeed = 0.0
						EndIf
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
					EndIf
                EndIf
                
				Local Temp% = MeNPCSeesPlayer(n)
				
				If chs\NoTarget Then Temp = False
				
                If Temp > False Then
					If n\LastSeen > 0 And n\LastSeen < 70.0 * 15.0 Then
						If Temp < 2 Then
							If n\Sound <> 0 Then 
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\ThereHeIs" + Rand(1, 6) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					Else
						If Temp = True
							If n\Sound <> 0 Then
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\Stop" + Rand(1, 6) + ".ogg")
							PlayMTFSound(n\Sound, n)
						ElseIf Temp = 2
							If n\Sound <> 0 Then
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\ClassD" + Rand(1, 4) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					EndIf
					
					n\LastSeen = 70.0 * Rand(30.0, 40.0)
					n\LastDist = 1.0
					
					n\State = 1.0
					n\EnemyX = EntityX(me\Collider, True)
					n\EnemyY = EntityY(me\Collider, True)
					n\EnemyZ = EntityZ(me\Collider, True)
					n\State2 = 70.0 * (15.0 * Temp)
					n\PathTimer = 0.0
					n\PathStatus = 0
					n\Reload = 200.0 - (100.0 * SelectedDifficulty\AggressiveNPCs)
				EndIf
				
				; ~ B3D doesn't do short-circuit evaluation, so this retarded nesting is an optimization
                If Curr173\Idle < 2 Then
					Local SoundVol173# = Max(Min((Distance(EntityX(Curr173\Collider), Curr173\PrevX, EntityZ(Curr173\Collider), Curr173\PrevZ) * 2.5), 1.0), 0.0)
					
					If OtherNPCSeesMeNPC(Curr173, n) Lor (SoundVol173 > 0.0 And EntityDistanceSquared(n\Collider, Curr173\Collider) < 36.0) Then
						If EntityVisible(n\Collider, Curr173\Collider) Lor SoundVol173 > 0.0 Then							
							n\State = 2.0
							n\EnemyX = EntityX(Curr173\Collider, True)
							n\EnemyY = EntityY(Curr173\Collider, True)
							n\EnemyZ = EntityZ(Curr173\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							
							If n\Sound <> 0 Then 
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\173\Spotted" + Rand(1, 2) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					EndIf
				EndIf
				
				If Curr106\State =< 0.0 Then
					If OtherNPCSeesMeNPC(Curr106, n) Lor EntityDistanceSquared(n\Collider, Curr106\Collider) < 9.0 Then
						If EntityVisible(n\Collider, Curr106\Collider) Then
							n\State = 4.0
							n\EnemyX = EntityX(Curr106\Collider, True)
							n\EnemyY = EntityY(Curr106\Collider, True)
							n\EnemyZ = EntityZ(Curr106\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							n\Target = Curr106
							
							If n\Sound <> 0 Then 
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\106\Spotted" + Rand(1, 3) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					EndIf
				EndIf
				
				If Curr096 <> Null Then
					If OtherNPCSeesMeNPC(Curr096, n) Then
						If EntityVisible(n\Collider, Curr096\Collider) Then
							n\State = 8.0
							n\EnemyX = EntityX(Curr096\Collider, True)
							n\EnemyY = EntityY(Curr096\Collider, True)
							n\EnemyZ = EntityZ(Curr096\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							
							If n\Sound <> 0 Then 
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\096\Spotted" + Rand(1, 2) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					EndIf
				ElseIf Curr049 <> Null
					If OtherNPCSeesMeNPC(Curr049, n) Then
						If EntityVisible(n\Collider, Curr049\Collider)
							n\State = 4.0
							n\EnemyX = EntityX(Curr049\Collider, True)
							n\EnemyY = EntityY(Curr049\Collider, True)
							n\EnemyZ = EntityZ(Curr049\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							n\Target = Curr049
							
							If n\Sound <> 0 Then 
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\049\Spotted" + Rand(1, 5) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					EndIf
				EndIf
				
				For n2.NPCs = Each NPCs
					If n2\NPCtype = NPCtype049_2 And (Not n2\IsDead)
						If OtherNPCSeesMeNPC(n2, n) Then
							If EntityVisible(n\Collider, n2\Collider)
								n\State = 9.0
								n\EnemyX = EntityX(n2\Collider, True)
								n\EnemyY = EntityY(n2\Collider, True)
								n\EnemyZ = EntityZ(n2\Collider, True)
								n\State2 = 70.0 * 15.0
								n\State3 = 0.0
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\Target = n2
								n\Reload = 70.0 * 5.0
								
								If n\Sound <> 0 Then 
									FreeSound_Strict(n\Sound) : n\Sound = 0
								EndIf
								n\Sound = LoadSound_Strict("SFX\Character\MTF\049_2\Spotted.ogg")
								PlayMTFSound(n\Sound, n)
								Exit
							EndIf
						EndIf
					ElseIf n2\NPCtype = NPCtype008_1 And (Not n2\IsDead)
						If OtherNPCSeesMeNPC(n2, n) Then
							If EntityVisible(n\Collider, n2\Collider)
								n\State = 9.0
								n\EnemyX = EntityX(n2\Collider, True)
								n\EnemyY = EntityY(n2\Collider, True)
								n\EnemyZ = EntityZ(n2\Collider, True)
								n\State2 = 70.0 * 15.0
								n\State3 = 0.0
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\Target = n2
								n\Reload = 70.0 * 5.0
								Exit
							EndIf
						EndIf
					ElseIf n2\NPCtype = NPCtype035_Tentacle And (Not n2\IsDead)
						If OtherNPCSeesMeNPC(n2, n) Then
							If EntityVisible(n\Collider, n2\Collider)
								n\State = 9.0
								n\EnemyX = EntityX(n2\Collider, True)
								n\EnemyY = EntityY(n2\Collider, True)
								n\EnemyZ = EntityZ(n2\Collider, True)
								n\State2 = 70.0 * 15.0
								n\State3 = 0.0
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\Target = n2
								n\Reload = 70.0 * 5.0
								Exit
							EndIf
						EndIf
					EndIf
				Next
                ;[End Block]
			Case 1.0
                ;[Block]
                n\Speed = 0.015
                n\State2 = n\State2 - fps\FPSFactor[0]
                If MeNPCSeesPlayer(n) = True Then
					Local Dist_To_Player# = EntityDistance(n\Collider, me\Collider)
					Local PrevKillTimer#
					
					If Dist_To_Player < 7.0 Then
						Local Angle# = VectorYaw(EntityX(me\Collider) - EntityX(n\Collider), 0.0, EntityZ(me\Collider) -EntityZ(n\Collider))
						
						RotateEntity(n\Collider, 0.0, CurveAngle(Angle, EntityYaw(n\Collider), 10.0), 0.0, True)
						n\Angle = EntityYaw(n\Collider)
						
						If n\Reload =< 0.0 And me\KillTimer = 0.0 Then
							If EntityVisible(n\Collider, Camera) Then
								Angle = WrapAngle(Angle - EntityYaw(n\Collider))
								If Angle < 5.0 Lor Angle > 355.0 Then 
									PrevKillTimer = me\KillTimer
									
									PlaySound2(GunshotSFX, Camera, n\Collider, 15.0)
									
									Pvt = CreatePivot()
									
									RotateEntity(Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0, True)
									PositionEntity(Pvt, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
									MoveEntity(Pvt, 0.8 * 0.079, 10.75 * 0.079, 6.9 * 0.079)
									
									Shoot(EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), ((25.0 / Dist_To_Player) * (1.0 / Dist_To_Player)), True)
									n\Reload = 7.0
									
									FreeEntity(Pvt)
									
									msg\DeathMsg = SubjectName + ". Died of blood loss after being shot by Nine-Tailed Fox."
									
									If PrevKillTimer >= 0.0 And me\KillTimer < 0.0 Then
										msg\DeathMsg = SubjectName + ". Terminated by Nine-Tailed Fox."
										PlayMTFSound(LoadTempSound("SFX\Character\MTF\Targetterminated" + Rand(1, 4) + ".ogg"), n)
									EndIf
								EndIf	
							EndIf
						EndIf
						
						For n2.NPCs = Each NPCs
							If n2\NPCtype = NPCtypeMTF And n2 <> n Then
								If n2\State = 0.0
									If EntityDistanceSquared(n\Collider, n2\Collider) < 36.0
										n\PrevState = 1
										n2\LastSeen = (70.0 * Rand(30.0, 40.0))
										n2\LastDist = 1.0
										
										n2\State = 1.0
										n2\EnemyX = EntityX(me\Collider, True)
										n2\EnemyY = EntityY(me\Collider, True)
										n2\EnemyZ = EntityZ(me\Collider, True)
										n2\State2 = n\State2
										n2\PathTimer = 0.0
										n2\PathStatus = 0
										n2\Reload = 200.0 - (100.0 * SelectedDifficulty\AggressiveNPCs)
										n2\PrevState = 0
									EndIf
								EndIf
							EndIf
						Next
						
						If n\PrevState = 1
							SetNPCFrame(n, 423.0)
							n\PrevState = 2
						ElseIf n\PrevState = 2
							If n\Frame > 200.0
								n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
								AnimateNPC(n, 423.0, 463.0, 0.4, False)
								If n\Frame > 462.9 Then n\Frame = 78.0
							Else
								AnimateNPC(n, 78.0, 193.0, 0.2, False)
								n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
							EndIf
						Else
							If n\Frame > 958.0 Then
								AnimateNPC(n, 1374.0, 1383.0, 0.3, False)
								n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
								If n\Frame > 1382.9 Then n\Frame = 78.0
							Else
								AnimateNPC(n, 78.0, 193.0, 0.2, False)
								n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
							EndIf
						EndIf
					Else
						PositionEntity(n\OBJ, n\EnemyX, n\EnemyY, n\EnemyZ, True)
						PointEntity(n\Collider, n\OBJ)
						RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
						
						n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
						TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
						AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
					EndIf
                Else
					n\LastSeen = n\LastSeen - fps\FPSFactor[0]
					
					If n\Reload =< 7.0
						n\Reload = 7.0
					EndIf
					
					If n\PathTimer =< 0.0 Then
						n\PathStatus = FindPath(n, n\EnemyX, n\EnemyY + 0.1, n\EnemyZ)
						n\PathTimer = 70.0 * Rnd(6.0, 10.0)
					ElseIf n\PathTimer =< 70.0 * 2.5 Then
						n\PathTimer = n\PathTimer - fps\FPSFactor[0]
						n\CurrSpeed = 0.0
						If Rand(1, 35) = 1 Then
							RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
						EndIf
						FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
					Else
						If n\PathStatus = 2 Then
							n\PathTimer = n\PathTimer - (fps\FPSFactor[0] * 2.0)
							n\CurrSpeed = 0.0
							If Rand(1, 35) = 1 Then
								RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
							EndIf
							FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
							n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
							RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
						ElseIf n\PathStatus = 1 Then
							If n\Path[n\PathLocation] = Null Then
								If n\PathLocation > 19 Then
									n\PathLocation = 0 : n\PathStatus = 0
								Else
									n\PathLocation = n\PathLocation + 1
								EndIf
							Else
								PrevDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
								
								PointEntity(n\Collider, n\Path[n\PathLocation]\OBJ)
								RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
								n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
								RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
								
								n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
								
								TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
								AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
								
								NewDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
								
								If NewDist < 1.0 And n\Path[n\PathLocation]\door <> Null Then
									; ~ Open the door and make it automatically close after 5 seconds
									If (Not n\Path[n\PathLocation]\door\Open)
										PlaySound2(OpenDoorSFX(n\Path[n\PathLocation]\door\DoorType, Rand(0, 2)), Camera, n\Path[n\PathLocation]\door\OBJ)
										PlayMTFSound(MTFSFX[0], n)
									EndIf
									n\Path[n\PathLocation]\door\Open = True
									If n\Path[n\PathLocation]\door\MTFClose
										n\Path[n\PathLocation]\door\TimerState = 70.0 * 5.0
									EndIf
								EndIf
								
								If (NewDist < 0.2) Lor ((PrevDist < NewDist) And (PrevDist < 1.0)) Then
									n\PathLocation = n\PathLocation + 1
								EndIf
							EndIf
							n\PathTimer = n\PathTimer - fps\FPSFactor[0]
						Else
							PositionEntity(n\OBJ, n\EnemyX, n\EnemyY, n\EnemyZ, True)
							If DistanceSquared(EntityX(n\Collider, True), n\EnemyX, EntityZ(n\Collider, True), n\EnemyZ) < 0.04 Lor (Not EntityVisible(n\OBJ, n\Collider)) Then
								If Rand(1, 35) = 1 Then
									RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
								EndIf
								FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
								If Rand(1, 35) = 1 Then
									For wp.Waypoints = Each WayPoints
										If (Rand(1, 3) = 1) Then
											If EntityDistanceSquared(wp\OBJ, n\Collider) < 36.0 Then
												n\EnemyX = EntityX(wp\OBJ, True)
												n\EnemyY = EntityY(wp\OBJ, True)
												n\EnemyZ = EntityZ(wp\OBJ, True)
												n\PathTimer = 0.0
												Exit
											EndIf											
										EndIf
									Next
								EndIf
								n\PathTimer = n\PathTimer - fps\FPSFactor[0]
							Else
								PointEntity(n\Collider, n\OBJ)
								RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
								n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
								RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
								
								n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
								TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
								AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
							EndIf
						EndIf
					EndIf
					
					If n\MTFLeader = Null And n\LastSeen < 70.0 * 30.0 And n\LastSeen + fps\FPSFactor[0] >= 70.0 * 30.0 Then
						If Rand(2) = 1 Then 
							PlayMTFSound(LoadTempSound("SFX\Character\MTF\Searching" + Rand(1, 6) + ".ogg"), n)
						EndIf
					EndIf
				EndIf
                
                If n\State2 =< 0.0 And n\State2 + fps\FPSFactor[0] > 0.0 Then
					If n\MTFLeader = Null Then
						PlayMTFSound(LoadTempSound("SFX\Character\MTF\Targetlost" + Rand(1, 3) + ".ogg"), n)
						If MTFCameraCheckTimer = 0.0
							If Rand(15 - (7 * SelectedDifficulty\AggressiveNPCs)) = 1 ; ~ Maybe change this to another chance -- ENDSHN
								PlayAnnouncement("SFX\Character\MTF\AnnouncCameraCheck.ogg")
								MTFCameraCheckTimer = fps\FPSFactor[0]
							EndIf
						EndIf
					EndIf
					n\State = 0.0
                EndIf
                
				; ~ B3D doesn't do short-circuit evaluation, so this retarded nesting is an optimization
                If Curr173\Idle < 2 Then
					SoundVol173 = Max(Min((Distance(EntityX(Curr173\Collider), Curr173\PrevX, EntityZ(Curr173\Collider), Curr173\PrevZ) * 2.5), 1.0), 0.0)
					If OtherNPCSeesMeNPC(Curr173, n) Lor (SoundVol173 > 0.0 And EntityDistanceSquared(n\Collider, Curr173\Collider) < 36.0) Then
						If EntityVisible(n\Collider, Curr173\Collider) Lor SoundVol173 > 0.0 Then	
							n\State = 2
							n\EnemyX = EntityX(Curr173\Collider, True)
							n\EnemyY = EntityY(Curr173\Collider, True)
							n\EnemyZ = EntityZ(Curr173\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							
							If n\Sound <> 0 Then 
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\173\Spotted" + Rand(1, 3) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					EndIf
				EndIf
				
				If Curr106\State =< 0.0 Then
					If OtherNPCSeesMeNPC(Curr106, n) Lor EntityDistanceSquared(n\Collider, Curr106\Collider) < 9.0 Then
						If EntityVisible(n\Collider, Curr106\Collider) Then
							n\State = 4.0
							n\EnemyX = EntityX(Curr106\Collider, True)
							n\EnemyY = EntityY(Curr106\Collider, True)
							n\EnemyZ = EntityZ(Curr106\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							n\Target = Curr106
							
							If n\MTFLeader = Null
								If n\Sound <> 0 Then 
									FreeSound_Strict(n\Sound) : n\Sound = 0
								EndIf
								n\Sound = LoadSound_Strict("SFX\Character\MTF\106\Spotted" + Rand(1, 4) + ".ogg")
								PlayMTFSound(n\Sound, n)
							EndIf
						EndIf
					EndIf
				EndIf
				
				If Curr096 <> Null Then
					If OtherNPCSeesMeNPC(Curr096, n) Then
						If EntityVisible(n\Collider, Curr096\Collider) Then
							n\State = 8.0
							n\EnemyX = EntityX(Curr096\Collider, True)
							n\EnemyY = EntityY(Curr096\Collider, True)
							n\EnemyZ = EntityZ(Curr096\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							
							If n\MTFLeader = Null
								If n\Sound <> 0 Then FreeSound_Strict(n\Sound) : n\Sound = 0
								n\Sound = LoadSound_Strict("SFX\Character\MTF\096\Spotted" + Rand(1, 2) + ".ogg")
								PlayMTFSound(n\Sound, n)
							EndIf
						EndIf
					EndIf
				ElseIf Curr049 <> Null
					If OtherNPCSeesMeNPC(Curr049, n) Then
						If EntityVisible(n\Collider, Curr049\Collider)
							n\State = 4.0
							n\EnemyX = EntityX(Curr049\Collider, True)
							n\EnemyY = EntityY(Curr049\Collider, True)
							n\EnemyZ = EntityZ(Curr049\Collider, True)
							n\State2 = 70.0 * 15.0
							n\State3 = 0.0
							n\PathTimer = 0.0
							n\PathStatus = 0
							n\Target = Curr049
							
							If n\Sound <> 0 Then
								FreeSound_Strict(n\Sound) : n\Sound = 0
							EndIf
							n\Sound = LoadSound_Strict("SFX\Character\MTF\049\Spotted" + Rand(1, 5) + ".ogg")
							PlayMTFSound(n\Sound, n)
						EndIf
					EndIf
				EndIf
				
				For n2.NPCs = Each NPCs
					If n2\NPCtype = NPCtype049_2 And (Not n2\IsDead)
						If OtherNPCSeesMeNPC(n2, n) Then
							If EntityVisible(n\Collider, n2\Collider)
								n\State = 9.0
								n\EnemyX = EntityX(n2\Collider, True)
								n\EnemyY = EntityY(n2\Collider, True)
								n\EnemyZ = EntityZ(n2\Collider, True)
								n\State2 = 70.0 * 15.0
								n\State3 = 0.0
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\Target = n2
								n\Reload = 70.0 * 5.0
								
								If n\Sound <> 0 Then 
									FreeSound_Strict(n\Sound) : n\Sound = 0
								EndIf
								n\Sound = LoadSound_Strict("SFX\Character\MTF\049_2\Spotted.ogg")
								PlayMTFSound(n\Sound, n)
								Exit
							EndIf
						EndIf
					ElseIf n2\NPCtype = NPCtype008_1 And (Not n2\IsDead)
						If OtherNPCSeesMeNPC(n2, n) Then
							If EntityVisible(n\Collider, n2\Collider)
								n\State = 9.0
								n\EnemyX = EntityX(n2\Collider, True)
								n\EnemyY = EntityY(n2\Collider, True)
								n\EnemyZ = EntityZ(n2\Collider, True)
								n\State2 = 70.0 * 15.0
								n\State3 = 0.0
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\Target = n2
								n\Reload = 70.0 * 5.0
								Exit
							EndIf
						EndIf
					ElseIf n2\NPCtype = NPCtype035_Tentacle And (Not n2\IsDead)
						If OtherNPCSeesMeNPC(n2, n) Then
							If EntityVisible(n\Collider, n2\Collider)
								n\State = 9.0
								n\EnemyX = EntityX(n2\Collider, True)
								n\EnemyY = EntityY(n2\Collider, True)
								n\EnemyZ = EntityZ(n2\Collider, True)
								n\State2 = 70.0 * 15.0
								n\State3 = 0.0
								n\PathTimer = 0.0
								n\PathStatus = 0
								n\Target = n2
								n\Reload = 70.0 * 5.0
								Exit
							EndIf
						EndIf
					EndIf
				Next
				;[End Block]
			Case 2.0 ; ~ Searching for / looking at SCP-173
                ;[Block]
                If Curr173\Idle = 2 Then
					n\State = 0.0
                Else
					For n2.NPCs = Each NPCs
						If n2 <> n Then
							If n2\NPCtype = NPCtypeMTF Then
								n2\State = 2.0
							EndIf
						EndIf
					Next
					
					Local Curr173Dist# = Distance(EntityX(n\Collider, True), EntityX(Curr173\Collider, True), EntityZ(n\Collider, True), EntityZ(Curr173\Collider, True))
					
					If Curr173Dist < 5.0 Then
						If Curr173\Idle <> 2 Then Curr173\Idle = 1
						n\State2 = 70.0 * 15.0
						n\PathTimer = 0.0
						
						Local TempDist# = 1.0
						
						If n\MTFLeader <> Null Then TempDist = 2.0
						If Curr173Dist < TempDist Then
							If n\MTFLeader = Null Then
								n\State3 = n\State3 + fps\FPSFactor[0]
								
								If n\State3 >= 70.0 * 30.0 Then
									Curr173\Idle = 2
									If n\MTFLeader = Null Then Curr173\Target = n
									If n\Sound <> 0 Then 
										FreeSound_Strict(n\Sound) : n\Sound = 0
									EndIf
									n\Sound = LoadSound_Strict("SFX\Character\MTF\173\Box" + Rand(1, 3) + ".ogg")
									PlayMTFSound(n\Sound, n)
								EndIf
							EndIf
							PositionEntity(n\OBJ, EntityX(Curr173\Collider, True), EntityY(Curr173\Collider, True), EntityZ(Curr173\Collider, True), True)
							PointEntity(n\Collider, n\OBJ)
							RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
							n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
							FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
							RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
						Else
							PositionEntity(n\OBJ, EntityX(Curr173\Collider, True), EntityY(Curr173\Collider, True), EntityZ(Curr173\Collider, True), True)
							PointEntity(n\Collider, n\OBJ)
							RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
							n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
							RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
							
							n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
							TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
							AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
						EndIf
					Else
						If Curr173\Idle <> 2 Then Curr173\Idle = 0
						If n\PathTimer =< 0.0 Then
							n\PathStatus = FindPath(n, EntityX(Curr173\Collider, True), EntityY(Curr173\Collider, True) + 0.1, EntityZ(Curr173\Collider, True))
							n\PathTimer = 70.0 * Rnd(6.0, 10.0)
						ElseIf n\PathTimer =< 70.0 * 2.5 Then
							n\PathTimer = n\PathTimer - fps\FPSFactor[0]
							n\CurrSpeed = 0.0
							If Rand(1, 35) = 1 Then
								RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
							EndIf
							FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
							n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
							RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
						Else
							If n\PathStatus = 2 Then
								n\PathTimer = n\PathTimer - (fps\FPSFactor[0] * 2.0)
								n\CurrSpeed = 0.0
								If Rand(1, 35) = 1 Then
									RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
								EndIf
								FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
								n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
								RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
							ElseIf n\PathStatus = 1 Then
								If n\Path[n\PathLocation] = Null Then
									If n\PathLocation > 19 Then
										n\PathLocation = 0 : n\PathStatus = 0
									Else
										n\PathLocation = n\PathLocation + 1
									EndIf
								Else
									PrevDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
									
									PointEntity(n\Collider, n\Path[n\PathLocation]\OBJ)
									RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
									n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
									RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
									
									n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
									
									TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
									AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
									
									NewDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
									
									If NewDist < 1.0 And n\Path[n\PathLocation]\door <> Null Then
										; ~ Open the door and make it automatically close after 5 seconds
										If (Not n\Path[n\PathLocation]\door\Open) Then
											PlaySound2(OpenDoorSFX(n\Path[n\PathLocation]\door\DoorType, Rand(0, 2)), Camera, n\Path[n\PathLocation]\door\OBJ)
											PlayMTFSound(MTFSFX[0], n)
										EndIf
										n\Path[n\PathLocation]\door\Open = True
										If n\Path[n\PathLocation]\door\MTFClose Then
											n\Path[n\PathLocation]\door\TimerState = 70.0 * 5.0
										EndIf
									EndIf
									
									If (NewDist < 0.2) Lor ((PrevDist < NewDist) And (PrevDist < 1.0)) Then
										n\PathLocation = n\PathLocation + 1
									EndIf
								EndIf
								n\PathTimer = n\PathTimer - fps\FPSFactor[0]
							Else
								n\PathTimer = n\PathTimer - (fps\FPSFactor[0] * 2.0)
								n\CurrSpeed = 0.0
								If Rand(1, 35) = 1 Then
									RotateEntity(n\Collider, 0.0, Rnd(360.0), 0.0, True)
								EndIf
								FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
								n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
								RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
							EndIf
						EndIf
					EndIf
                EndIf
                ;[End Block]
			Case 3.0 ; ~ Following a path
				;[Block]
				n\Angle = CurveValue(0.0, n\Angle, 40.0)
				
				If n\PathStatus = 2 Then
					n\State = 5.0
					n\CurrSpeed = 0.0
				ElseIf n\PathStatus = 1
					If n\Path[n\PathLocation] = Null Then 
						If n\PathLocation > 19 Then 
							n\PathLocation = 0
							n\PathStatus = 0
							If n\LastSeen > 0 Then n\State = 5.0
						Else
							n\PathLocation = n\PathLocation + 1
						EndIf
					Else
						If n\Path[n\PathLocation]\door <> Null Then
							If (Not n\Path[n\PathLocation]\door\Open) Then
								n\Path[n\PathLocation]\door\Open = True
								n\Path[n\PathLocation]\door\TimerState = 70.0 * 8.0
								PlayMTFSound(MTFSFX[0], n)
							EndIf
						EndIf
						
						If Dist < HideDistance * 0.7 Then 
							Dist2 = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ) 
							
							PointEntity(n\OBJ, n\Path[n\PathLocation]\OBJ)
							
							RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 10.0), 0.0)
							If n\Idle = 0 Then
								n\CurrSpeed = CurveValue(n\Speed * Max(Min(Dist2, 1.0), 0.1), n\CurrSpeed, 20.0)
								MoveEntity(n\Collider, 0.0, 0.0, n\CurrSpeed * fps\FPSFactor[0])
								
								If EntityDistanceSquared(n\Collider, n\Path[n\PathLocation]\OBJ) < 0.25
									n\PathLocation = n\PathLocation + 1
								EndIf
							EndIf
						Else
							If Rand(20) = 1 Then 
								PositionEntity(n\Collider, EntityX(n\Path[n\PathLocation]\OBJ, True), EntityY(n\Path[n\PathLocation]\OBJ, True) + 0.25, EntityZ(n\Path[n\PathLocation]\OBJ, True), True)
								n\PathLocation = n\PathLocation + 1
								ResetEntity(n\Collider)
							EndIf
						EndIf
					EndIf
				Else
					n\CurrSpeed = 0.0
					n\State = 5.0
				EndIf
				
				If n\Idle = 0 And n\PathStatus = 1 Then
					If Dist < HideDistance Then
						If n\Frame > 959.0 Then
							AnimateNPC(n, 1376.0, 1383.0, 0.2, False)
							If n\Frame > 1382.9 Then n\Frame = 488.0
						Else
							AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 30.0)
						EndIf
					EndIf
				Else
					If Dist < HideDistance Then
						If n\LastSeen > 0 Then 
							AnimateNPC(n, 78.0, 312.0, 0.2, True)
						Else
							If n\Frame < 962.0 Then
								If n\Frame > 487.0 Then n\Frame = 463.0
								AnimateNPC(n, 463.0, 487.0, 0.3, False)
								If n\Frame > 486.9 Then n\Frame = 962.0
							Else
								AnimateNPC(n, 962.0, 1259.0, 0.3)
							EndIf
						EndIf
					EndIf
					
					n\CurrSpeed = CurveValue(0.0, n\CurrSpeed, 20.0)
				EndIf
				
				n\Angle = EntityYaw(n\Collider)
				;[End Block]
			Case 4.0 ; ~ SCP-106 or SCP-049 detected
				;[Block]
				n\Speed = 0.03
                n\State2 = n\State2 - fps\FPSFactor[0]
				If n\State2 > 0.0
					If OtherNPCSeesMeNPC(n\Target, n)
						n\State2 = 70.0 * 15.0
					EndIf
					
					If EntityDistanceSquared(n\Target\Collider, n\Collider) > PowTwo(HideDistance)
						If n\State2 > 70.0
							n\State2 = 70.0
						EndIf
					EndIf
					
					If EntityDistanceSquared(n\Target\Collider, n\Collider) < 9.0 And n\State3 >= 0.0
						n\State3 = 70.0 * 5.0
					EndIf
					
					If n\State3 > 0.0
						n\PathStatus = 0
						n\PathLocation = 0
						n\Speed = 0.02
						PointEntity(n\Collider, n\Target\Collider)
						RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
						n\CurrSpeed = CurveValue(-n\Speed, n\CurrSpeed, 20.0)
						TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
						AnimateNPC(n, 522.0, 488.0, n\CurrSpeed * 26.0)
						
						n\PathTimer = 1.0
						
						n\State3 = Max(n\State3 - fps\FPSFactor[0], 0.0)
						
						HideEntity(n\Collider)
						TurnEntity(n\Collider, 0.0, 180.0, 0.0)
						EntityPick(n\Collider, 1.0)
						If PickedEntity() <> 0 Then
							n\State3 = (-70.0) * 2.0
						EndIf
						ShowEntity(n\Collider)
						TurnEntity(n\Collider, 0.0, 180.0, 0.0)
					ElseIf n\State3 < 0.0
						n\State3 = Min(n\State3 + fps\FPSFactor[0], 0.0)
					EndIf
					
					If n\PathTimer =< 0.0 Then
						If n\MTFLeader <> Null Then
							n\PathStatus = FindPath(n, EntityX(n\MTFLeader\Collider, True), EntityY(n\MTFLeader\Collider, True) + 0.1, EntityZ(n\MTFLeader\Collider, True))
						Else
							For r = Each Rooms
								If ((Abs(r\x - EntityX(n\Collider, True)) > 12.0) Lor (Abs(r\z - EntityZ(n\Collider, True)) > 12.0)) And (Rand(1, Max(4 - Int(Abs(r\z - EntityZ(n\Collider, True) / 8.0)), 2)) = 1) Then
									If EntityDistanceSquared(r\OBJ, n\Target\Collider) > 36.0
										x = r\x
										y = 0.1
										z = r\z
										Exit
									EndIf
								EndIf
							Next
							n\PathStatus = FindPath(n, x, y, z)
						EndIf
						If n\PathStatus = 1 Then
							While n\Path[n\PathLocation] = Null
								If n\PathLocation > 19 Then Exit
								n\PathLocation = n\PathLocation + 1
							Wend
							If n\PathLocation < 19 Then
								If (n\Path[n\PathLocation] <> Null) And (n\Path[n\PathLocation + 1] <> Null) Then
									If (n\Path[n\PathLocation]\door = Null) Then
										If Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation]\OBJ)) > Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation + 1]\OBJ)) Then
											n\PathLocation = n\PathLocation + 1
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
						n\PathTimer = 70.0 * 10.0
					Else
						If n\PathStatus = 1 Then
							If n\Path[n\PathLocation] = Null Then
								If n\PathLocation > 19 Then
									n\PathLocation = 0 : n\PathStatus = 0
								Else
									n\PathLocation = n\PathLocation + 1
								EndIf
							Else
								PrevDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
								
								PointEntity(n\Collider, n\Path[n\PathLocation]\OBJ)
								RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
								n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
								RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
								
								n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
								TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
								AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
								
								NewDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
								
								If NewDist < 2.0 And n\Path[n\PathLocation]\door <> Null Then
									If (Not n\Path[n\PathLocation]\door\Open)
										PlaySound2(OpenDoorSFX(n\Path[n\PathLocation]\door\DoorType, Rand(0, 2)), Camera, n\Path[n\PathLocation]\door\OBJ)
										PlayMTFSound(MTFSFX[0], n)
									EndIf
									n\Path[n\PathLocation]\door\Open = True
									If n\Path[n\PathLocation]\door\MTFClose
										n\Path[n\PathLocation]\door\TimerState = 70.0 * 5.0
									EndIf
								EndIf
								
								If (NewDist < 0.2) Lor ((PrevDist < NewDist) And (PrevDist < 1.0)) Then
									n\PathLocation = n\PathLocation + 1
								EndIf
							EndIf
							n\PathTimer = n\PathTimer - fps\FPSFactor[0]
						Else
							n\PathTimer = 0.0
						EndIf
					EndIf
				Else
					n\State = 0.0
				EndIf
				;[End Block]
			Case 5.0 ; ~ Looking at some other target than the player
				;[Block]
				Target = CreatePivot()
				PositionEntity(Target, n\EnemyX, n\EnemyY, n\EnemyZ, True)
				
				If Dist < HideDistance Then
					AnimateNPC(n, 79.0, 194.0, 0.2)
				EndIf
				
				If Abs(EntityX(Target) - EntityX(n\Collider)) < 55.0 And Abs(EntityZ(Target) - EntityZ(n\Collider)) < 55.0 And Abs(EntityY(Target) - EntityY(n\Collider)) < 20.0 Then
					
					PointEntity(n\OBJ, Target)
					RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 30.0), 0.0, True)
					
					If n\PathTimer = 0 Then
						n\PathStatus = EntityVisible(n\Collider, Target)
						n\PathTimer = Rand(100.0, 200.0)
					Else
						n\PathTimer = Min(n\PathTimer - fps\FPSFactor[0], 0.0)
					EndIf
					
					If n\PathStatus = 1 And n\Reload =< 0.0 Then
						Dist = Distance(EntityX(Target), EntityX(n\Collider), EntityZ(Target), EntityZ(n\Collider))
					EndIf
				EndIf		
				
				FreeEntity(Target)
				
				n\Angle = EntityYaw(n\Collider)
				;[End Block]
			Case 6.0 ; ~ Seeing the player as SCP-049-2 instance
				;[Block]
				PointEntity(n\OBJ, me\Collider)
				RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
				n\Angle = EntityYaw(n\Collider)
				
				AnimateNPC(n, 79.0, 194.0, 0.2)
				
				If n\Reload =< 0.0 And me\KillTimer = 0.0 Then
					If EntityVisible(n\Collider, me\Collider) Then
						If (Abs(DeltaYaw(n\Collider, me\Collider)) < 50.0)
							PlaySound2(GunshotSFX, Camera, n\Collider, 15.0)
							
							Pvt = CreatePivot()
							
							RotateEntity(Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0, True)
							PositionEntity(Pvt, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
							MoveEntity(Pvt, 0.8 * 0.079, 10.75 * 0.079, 6.9 * 0.079)
							
							Shoot(EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), 0.9)
							n\Reload = 7.0
							
							FreeEntity(Pvt)
						EndIf	
					EndIf
				EndIf
				;[End Block]
			Case 7.0 ; ~ Just shooting
				;[Block]
				AnimateNPC(n, 346.0, 351.0, 0.6)
				
				RotateEntity(n\Collider, 0.0, CurveAngle(n\State2, EntityYaw(n\Collider), 20.0), 0.0)
				n\Angle = EntityYaw(n\Collider)
				
				If n\Reload =< 0.0 Then
					PlaySound2(GunshotSFX, Camera, n\Collider, 20.0)
					
					Pvt = CreatePivot()
					
					RotateEntity(Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0, True)
					PositionEntity(Pvt, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
					MoveEntity(Pvt, 0.8 * 0.079, 10.75 * 0.079, 6.9 * 0.079)
					
					p.Particles = CreateParticle(EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), 1, Rnd(0.08, 0.1), 0.0, 5.0)
					p\Achange = -0.15
					TurnEntity(p\OBJ, 0.0, 0.0, Rnd(360.0))
					
					FreeEntity(Pvt)
					n\Reload = 7.0
				EndIf
				;[End Block]
			Case 8.0 ;SCP-096 spotted
				;[Block]
				n\Speed = 0.015
				
                If n\PathTimer =< 0.0 Then
					If n\MTFLeader <> Null Then
						n\PathStatus = FindPath(n, EntityX(n\MTFLeader\Collider, True), EntityY(n\MTFLeader\Collider, True) + 0.1, EntityZ(n\MTFLeader\Collider, True))
					Else 
						For r = Each Rooms
							If ((Abs(r\x - EntityX(n\Collider, True)) > 12.0) Lor (Abs(r\z - EntityZ(n\Collider, True)) > 12.0)) And (Rand(1, Max(4 - Int(Abs(r\z - EntityZ(n\Collider, True) / 8.0)), 2)) = 1) Then
								x = r\x
								y = 0.1
								z = r\z
								Exit
							EndIf
						Next
						n\PathStatus = FindPath(n, x, y, z)
					EndIf
					If n\PathStatus = 1 Then
						While n\Path[n\PathLocation] = Null
							If n\PathLocation > 19 Then Exit
							n\PathLocation = n\PathLocation + 1
						Wend
						If n\PathLocation < 19 Then
							If (n\Path[n\PathLocation] <> Null) And (n\Path[n\PathLocation + 1] <> Null) Then
								If (n\Path[n\PathLocation]\door = Null) Then
									If Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation]\OBJ)) > Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation + 1]\OBJ)) Then
										n\PathLocation = n\PathLocation + 1
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
					n\PathTimer = 70.0 * Rnd(6.0, 10.0)
                ElseIf n\PathTimer =< 70.0 * 2.5 And n\MTFLeader = Null Then
					n\PathTimer = n\PathTimer - fps\FPSFactor[0]
					n\CurrSpeed = 0.0
					
					FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
					n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
					RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
                Else
					If n\PathStatus = 2 Then
						n\PathTimer = n\PathTimer - (fps\FPSFactor[0] * 2.0)
						n\CurrSpeed = 0.0
						
						FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
					ElseIf n\PathStatus = 1 Then
						If n\Path[n\PathLocation] = Null Then
							If n\PathLocation > 19 Then
								n\PathLocation = 0 : n\PathStatus = 0
							Else
								n\PathLocation = n\PathLocation + 1
							EndIf
						Else
							PrevDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
							
							PointEntity(n\Collider, n\Path[n\PathLocation]\OBJ)
							RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
							n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
							RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
							
							n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
							
							TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
							AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
							
							NewDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
							
							If (NewDist < 1.0 And n\Path[n\PathLocation]\door <> Null) Then
								; ~ Open the door and make it automatically close after 5 seconds
								If (Not n\Path[n\PathLocation]\door\Open) Then
									PlaySound2(OpenDoorSFX(n\Path[n\PathLocation]\door\DoorType, Rand(0, 2)), Camera, n\Path[n\PathLocation]\door\OBJ)
									PlayMTFSound(MTFSFX[0], n)
								EndIf
								n\Path[n\PathLocation]\door\Open = True
								If n\Path[n\PathLocation]\door\MTFClose
									n\Path[n\PathLocation]\door\TimerState = 70.0 * 5.0
								EndIf
							EndIf
                            
							If (NewDist < 0.2) Lor ((PrevDist < NewDist) And (PrevDist < 1.0)) Then
								n\PathLocation = n\PathLocation + 1
							EndIf
						EndIf
						n\PathTimer = n\PathTimer - fps\FPSFactor[0]
					Else
						n\PathTimer = n\PathTimer - (fps\FPSFactor[0] * 2.0)
						If n\MTFLeader = Null Then
							FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
							n\CurrSpeed = 0.0
						ElseIf EntityDistanceSquared(n\Collider, n\MTFLeader\Collider) > 1.21 Then
							PointEntity(n\Collider, n\MTFLeader\Collider)
							RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
							
							n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
							TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
							AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
						Else
							FinishWalking(n, 488.0, 522.0, n\Speed * 26.0)
							n\CurrSpeed = 0.0
						EndIf
						n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
						RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
					EndIf
                EndIf
				
				If (Not EntityVisible(n\Collider, Curr096\Collider)) Lor EntityDistanceSquared(n\Collider, Curr096\Collider) > 36.0 Then
					n\State = 0.0
				EndIf
				;[End Block]
			Case 9.0 ;SCP-049-2 or SCP-008-1 or SCP-035's tentacle spotted
				;[Block]
				If EntityVisible(n\Collider, n\Target\Collider) Then
					PointEntity(n\OBJ, n\Target\Collider)
					RotateEntity(n\Collider, 0.0, CurveAngle(EntityYaw(n\OBJ), EntityYaw(n\Collider), 20.0), 0.0)
					n\Angle = EntityYaw(n\Collider)
					
					If EntityDistanceSquared(n\Target\Collider, n\Collider) < 1.69 Then
						n\State3 = 70.0 * 2.0
					EndIf
					
					If n\State3 > 0.0
						n\PathStatus = 0
						n\PathLocation = 0
						n\Speed = 0.02
						n\CurrSpeed = CurveValue(-n\Speed, n\CurrSpeed, 20.0)
						TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
						AnimateNPC(n, 522.0, 488.0, n\CurrSpeed * 26.0)
						
						n\PathTimer = 1.0
						
						n\State3 = Max(n\State3 - fps\FPSFactor[0], 0.0)
					Else
						n\State3 = 0.0
					EndIf
					If n\Reload =< 0.0 And (Not n\Target\IsDead) Then
						If (Abs(DeltaYaw(n\Collider, n\Target\Collider)) < 50.0)
							AnimateNPC(n, 346.0, 351.0, 0.6)
							
							PlaySound2(GunshotSFX, Camera, n\Collider, 15.0)
							
							Pvt = CreatePivot()
							
							RotateEntity(Pvt, EntityPitch(n\Collider), EntityYaw(n\Collider), 0.0, True)
							PositionEntity(Pvt, EntityX(n\OBJ), EntityY(n\OBJ), EntityZ(n\OBJ))
							MoveEntity(Pvt, 0.8 * 0.079, 10.75 * 0.079, 6.9 * 0.079)
							
							p.Particles = CreateParticle(EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), 1, Rnd(0.08, 0.1), 0.0, 5.0)
							p\Achange = -0.15
							TurnEntity(p\OBJ, 0.0, 0.0, Rnd(360.0))
							
							If n\Target\HP > 0 Then
								n\Target\HP = Max(n\Target\HP - Rand(5, 10), 0.0)
							Else
								If (Not n\Target\IsDead) Then
									If n\Sound <> 0 Then 
										FreeSound_Strict(n\Sound) : n\Sound = 0
									EndIf	
									If n\Target\NPCtype = NPCtype049_2
										n\Sound = LoadSound_Strict("SFX\Character\MTF\049_2\TargetTerminated.ogg")
										PlayMTFSound(n\Sound, n)
									EndIf
								EndIf
								SetNPCFrame(n\Target, 133.0)
								n\Target\IsDead = True
								n\Target = Null
								n\State = 0.0
								Return
							EndIf
							n\Reload = 7.0
							
							FreeEntity(Pvt)
						EndIf	
					EndIf
					n\PathStatus = 0
				Else
					If n\PathTimer =< 0.0 Then
						n\PathStatus = FindPath(n, EntityX(n\Target\Collider), EntityY(n\Target\Collider), EntityZ(n\Target\Collider))
						If n\PathStatus = 1 Then
							While n\Path[n\PathLocation] = Null
								If n\PathLocation > 19 Then Exit
								n\PathLocation = n\PathLocation + 1
							Wend
							If n\PathLocation < 19 Then
								If (n\Path[n\PathLocation] <> Null) And (n\Path[n\PathLocation + 1] <> Null) Then
									If (n\Path[n\PathLocation]\door = Null) Then
										If Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation]\OBJ)) > Abs(DeltaYaw(n\Collider, n\Path[n\PathLocation + 1]\OBJ)) Then
											n\PathLocation = n\PathLocation + 1
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
						n\PathTimer = 70.0 * 10.0
					Else
						If n\PathStatus = 1 Then
							If n\Path[n\PathLocation] = Null Then
								If n\PathLocation > 19 Then
									n\PathLocation = 0 : n\PathStatus = 0
								Else
									n\PathLocation = n\PathLocation + 1
								EndIf
							Else
								PrevDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
								
								PointEntity(n\Collider, n\Path[n\PathLocation]\OBJ)
								RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
								n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
								RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
								
								n\CurrSpeed = CurveValue(n\Speed, n\CurrSpeed, 20.0)
								TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * n\CurrSpeed * fps\FPSFactor[0], True)
								AnimateNPC(n, 488.0, 522.0, n\CurrSpeed * 26.0)
								
								NewDist = EntityDistance(n\Collider, n\Path[n\PathLocation]\OBJ)
								
								; ~ Open the door and make it automatically close after 5 seconds
								If NewDist < 1.0 And n\Path[n\PathLocation]\door <> Null Then
									If (Not n\Path[n\PathLocation]\door\Open)
										PlaySound2(OpenDoorSFX(n\Path[n\PathLocation]\door\DoorType, Rand(0, 2)), Camera, n\Path[n\PathLocation]\door\OBJ)
										PlayMTFSound(MTFSFX[0], n)
									EndIf
									n\Path[n\PathLocation]\door\Open = True
									If n\Path[n\PathLocation]\door\MTFClose
										n\Path[n\PathLocation]\door\TimerState = 70.0 * 5.0
									EndIf
								EndIf
								
								If (NewDist < 0.2) Lor ((PrevDist < NewDist) And (PrevDist < 1.0)) Then
									n\PathLocation = n\PathLocation + 1
								EndIf
							EndIf
							n\PathTimer = n\PathTimer - fps\FPSFactor[0]
						Else
							n\PathTimer = 0.0
						EndIf
					EndIf
				EndIf
				
				If n\Target\IsDead Then
					n\Target = Null
					n\State = 0.0
				EndIf
				;[End Block]
		End Select
		
		If n\CurrSpeed > 0.01 Then
			If PrevFrame > 500.0 And n\Frame < 495.0
				PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))
			ElseIf PrevFrame < 505.0 And n\Frame >= 505.0
				PlaySound2(StepSFX(4, 0, Rand(0, 2)), Camera, n\Collider, 8.0, Rnd(0.5, 0.7))
			EndIf
		EndIf
		
		If chs\NoTarget And n\State = 1.0 Then n\State = 0.0
		
		If n\State <> 3.0 And n\State <> 5.0 And n\State <> 6.0 And n\State <> 7.0 Then
			If n\MTFLeader <> Null Then
				If EntityDistanceSquared(n\Collider, n\MTFLeader\Collider) < 0.49 Then
					PointEntity(n\Collider, n\MTFLeader\Collider)
					RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider, True), 0.0, True)
					n\Angle = CurveAngle(EntityYaw(n\Collider, True), n\Angle, 20.0)
					
					TranslateEntity(n\Collider, Cos(EntityYaw(n\Collider, True) - 45.0) * 0.01 * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) - 45.0) * 0.01 * fps\FPSFactor[0], True)
				EndIf
			Else
				For n2.NPCs = Each NPCs
					If n2 <> n And (Not n2\IsDead) Then
						If Abs(DeltaYaw(n\Collider, n2\Collider)) < 80.0 Then
							If EntityDistanceSquared(n\Collider, n2\Collider) < 0.49 Then							
								TranslateEntity(n2\Collider, Cos(EntityYaw(n\Collider, True) + 90.0) * 0.01 * fps\FPSFactor[0], 0.0, Sin(EntityYaw(n\Collider, True) + 90.0) * 0.01 * fps\FPSFactor[0], True)
							EndIf
						EndIf
					EndIf
				Next
			EndIf
		EndIf
		
		; ~ Teleport back to the facility if fell through the floor
		If n\State <> 6.0 And n\State <> 7.0
			If EntityY(n\Collider) < -10.0 Then
				TeleportCloser(n)
			EndIf
		EndIf
		
		RotateEntity(n\OBJ, -90.0, n\Angle, 0.0, True)
		
		PositionEntity(n\OBJ, EntityX(n\Collider, True), EntityY(n\Collider, True) - 0.15, EntityZ(n\Collider, True), True)
	EndIf
End Function

Function Shoot(x#, y#, z#, HitProb# = 1.0, Particles% = True, InstaKill% = False)  
    Local p.Particles, de.Decals, n.NPCs
	Local Pvt%, ShotMessageUpdate$, i%
	
	p.Particles = CreateParticle(x, y, z, 1, Rnd(0.08, 0.1), 0.0, 5.0)
	p\Achange = -0.15
	TurnEntity(p\OBJ, 0.0, 0.0, Rnd(360.0))
	
	If InstaKill Then Kill(True) : PlaySound_Strict(BullethitSFX) : Return
	
	If Rnd(1.0) =< HitProb Then
		TurnEntity(Camera, Rnd(-3.0, 3.0), Rnd(-3.0, 3.0), 0.0)
		If wi\BallisticVest > 0 And wi\BallisticHelmet = 0 Then ; ~ If player is wearing the ballistic vest only
			Select Rand(16)
				Case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ; ~ Vest
					;[Block]
					InjurePlayer(Rnd(0.1, 0.2))
					ShotMessageUpdate = "A bullet penetrated your vest, making you gasp."
					;[End Block]
				Case 11 ; ~ Left Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.9), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left leg."
					;[End Block]
				Case 12 ; ~ Right Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.9), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right leg."
					;[End Block]
				Case 13 ; ~ Left Arm
					;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left arm."
					;[End Block]
				Case 14 ; ~ Right Arm
					;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right arm."
					;[End Block]
				Case 15 ; ~ Neck
					;[Block]
					InjurePlayer(Rnd(1.1, 1.3), 0.0, 650.0)
					ShotMessageUpdate = "A bullet struck your neck, making you gasp."
					;[End Block]
				Case 16 ; ~ Face or Head
					;[Block]
					For n.NPCs = Each NPCs
						If n\NPCtype = NPCtypeMTF Lor n\NPCtype = NPCtypeApache Lor n\NPCtype = NPCtypeGuard
							If EntityInView(n\OBJ, Camera) Then
								ShotMessageUpdate = "A bullet hit your face."
							Else
								ShotMessageUpdate = "A bullet hit your head."
							EndIf
							Kill(True)
						EndIf
					Next
					;[End Block]
			End Select	
		ElseIf wi\BallisticVest > 0 And wi\BallisticHelmet > 0 Then ; ~ If player is wearing the ballistic vest and the ballistic helmet
			Select Rand(22)
				Case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ; ~ Vest
					;[Block]
					InjurePlayer(Rnd(0.1, 0.2))
					ShotMessageUpdate = "A bullet penetrated your vest, making you gasp."
					;[End Block]
				Case 11 ; ~ Left Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.9), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left leg."
					;[End Block]
				Case 12 ; ~ Right Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.9), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right leg."
					;[End Block]
				Case 13 ; ~ Left Arm
					;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left arm."
					;[End Block]
				Case 14 ; ~ Right Arm
					;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right arm."
					;[End Block]
				Case 15 ;Neck
					;[Block]
					InjurePlayer(Rnd(1.1, 1.3), 0.0, 650.0)
					ShotMessageUpdate = "A bullet struck your neck, making you gasp."
					;[End Block]
				Case 16, 17, 18, 19, 20, 21, 22 ; ~ Helmet
					;[Block]
					ShotMessageUpdate = "A bullet hit your helmet."
					;[End Block]
			End Select	
		ElseIf wi\BallisticVest = 0 And wi\BallisticHelmet > 0 ; ~ If player is wearing the ballistic helmet only
			Select Rand(16)
				Case 1, 2, 3, 4, 5, 6, 7 ; ~ Helmet
					;[Block]
					ShotMessageUpdate = "A bullet hit your helmet."
					;[End Block]
				Case 8 ; ~ Left Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.8), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left leg."
					;[End Block]
				Case 9 ; ~ Right Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.8), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right leg."
					;[End Block]
				Case 10 ; ~ Left Arm
					;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left arm."
					;[End Block]
				Case 11 ; ~ Right Arm
					;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right arm."
					;[End Block]
				Case 12 ;Right Shoulder
					;[Block]
				    InjurePlayer(Rnd(0.9, 1.1), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right shoulder."
					;[End Block]	
				Case 13 ; ~ Left Shoulder
					;[Block]
					InjurePlayer(Rnd(0.9, 1.1), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left shoulder."
					;[End Block]
			    Case 14 ; ~ Right Shoulder (more damage)
					;[Block]
					InjurePlayer(Rnd(2.4, 2.6), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right shoulder."
					;[End Block]
				Case 15 ; ~ Left Shoulder (more damage)
					;[Block]
					InjurePlayer(Rnd(2.4, 2.6), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left shoulder."
					;[End Block]
				Case 16 ; ~ Neck
					;[Block]
					InjurePlayer(Rnd(1.1, 1.3), 0.0, 650.0)
					ShotMessageUpdate = "A bullet struck your neck, making you gasp."
					;[End Block]
			End Select
		ElseIf wi\BallisticVest = 0 And wi\BallisticHelmet = 0 ; ~ If player isn't wearing the ballistic vest and the ballistic helmet
			Select Rand(10)
				Case 1 ; ~ Left Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.9), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left leg."
					;[End Block]
			    Case 2 ; ~ Right Leg
					;[Block]
					me\Stamina = 0.0
					InjurePlayer(Rnd(0.7, 0.9), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right leg."
					;[End Block]
				Case 3 ; ~ Right Shoulder
					;[Block]
					InjurePlayer(Rnd(0.9, 1.1), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right shoulder."
					;[End Block]	
				Case 4 ; ~ Left Shoulder
					;[Block]
					InjurePlayer(Rnd(0.9, 1.1), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left shoulder."
					;[End Block]
				Case 5 ; ~ Right Shoulder (more damage)
					;[Block]
					InjurePlayer(Rnd(2.4, 2.6), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your right shoulder."
					;[End Block]
				Case 6 ; ~ Left Shoulder (more damage)
					;[Block]
					InjurePlayer(Rnd(2.4, 2.6), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left shoulder."
					;[End Block]
				Case 7 ; ~ Left Arm
					;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
					ShotMessageUpdate = "A bullet hit your left arm."
					;[End Block]
				Case 8 ; ~ Right Arm
				    ;[Block]
					InjurePlayer(Rnd(0.5, 0.7), 0.0, 650.0)
				    ShotMessageUpdate = "A bullet hit your right arm."
					;[End Block]
				Case 9 ; ~ Neck
					;[Block]
					InjurePlayer(Rnd(1.1, 1.3), 0.0, 650.0)
				    ShotMessageUpdate = "A bullet struck your neck, making you gasp."
					;[End Block]
                Case 10 ; ~ Face or Head
					;[Block]
					For n.NPCs = Each NPCs
						If n\NPCtype = NPCtypeMTF Lor n\NPCtype = NPCtypeApache Lor n\NPCtype = NPCtypeGuard
							If EntityInView(n\OBJ, Camera) Then
								ShotMessageUpdate = "A bullet hit your face."
							Else
								ShotMessageUpdate = "A bullet hit your head."
							EndIf
							Kill(True)
						EndIf
					Next
					;[End Block]
		    End Select
		EndIf
		
		If msg\Timer < 70.0 * 5.0 Then
			CreateMsg(ShotMessageUpdate, 6.0)
		EndIf
		
		If me\Injuries >= 10.0 Then Kill(True)
		
		PlaySound_Strict(BullethitSFX)
	ElseIf Particles And opt\ParticleAmount > 0
		Pvt = CreatePivot()
		PositionEntity(Pvt, EntityX(me\Collider), (EntityY(me\Collider) + EntityY(Camera)) / 2.0, EntityZ(me\Collider))
		PointEntity(Pvt, p\OBJ)
		TurnEntity(Pvt, 0.0, 180.0, 0.0)
		
		EntityPick(Pvt, 2.5)
		
		If PickedEntity() <> 0 Then 
			PlaySound2(Gunshot3SFX, Camera, Pvt, 0.4, Rnd(0.8, 1.0))
			
			If Particles Then 
				p.Particles = CreateParticle(PickedX(), PickedY(), PickedZ(), 0, 0.03, 0.0, 80.0)
				p\Speed = 0.001 : p\SizeChange = 0.003 : p\A = 0.8 : p\Achange = -0.01
				RotateEntity(p\Pvt, EntityPitch(Pvt) - 180.0, EntityYaw(Pvt), 0)
				
				For i = 0 To Rand(2, 3)
					p.Particles = CreateParticle(PickedX(), PickedY(), PickedZ(), 0, 0.006, 0.003, 80.0)
					p\Speed = 0.02 : p\A = 0.8 : p\Achange = -0.01
					RotateEntity(p\Pvt, EntityPitch(Pvt) + Rnd(170.0, 190.0), EntityYaw(Pvt) + Rnd(-10.0, 10.0), 0)	
				Next
				
				de.Decals = CreateDecal(Rand(13, 14), PickedX(), PickedY() + Rnd(-0.05, 0.05), PickedZ(), Rnd(-4.0, 4.0), Rnd(-4.0, 4.0), Rnd(-4.0, 4.0), Rnd(0.028, 0.034), 1.0, 1, 2)
				de\LifeTime = 70.0 * 20.0
				AlignToVector(de\OBJ, -PickedNX(), -PickedNY(), -PickedNZ(), 3)
				MoveEntity(de\OBJ, 0.0, 0.0, -0.001)
			EndIf				
		EndIf	
		FreeEntity(Pvt)
	EndIf
End Function

Function MoveToPocketDimension()
	Local r.Rooms
	
	For r.Rooms = Each Rooms
		If r\RoomTemplate\Name = "pocketdimension" Then
			me\FallTimer = 0.0
			UpdateDoors()
			UpdateRooms()
			ShowEntity(me\Collider)
			PlaySound_Strict(Use914SFX)
			PlaySound_Strict(OldManSFX[5])
			PositionEntity(me\Collider, EntityX(r\OBJ), 0.8, EntityZ(r\OBJ))
			me\DropSpeed = 0.0
			ResetEntity(me\Collider)
			
			me\BlinkTimer = -10.0
			
			InjurePlayer(0.5, 0.0, 1600.0)
			
			PlayerRoom = r
			
			Return
		EndIf
	Next		
End Function

Function FindFreeNPCID%()
	Local n2.NPCs
	Local ID% = 1
	
	While True
		Local Taken% = False
		
		For n2.NPCs = Each NPCs
			If n2\ID = ID Then
				Taken = True
				Exit
			EndIf
		Next
		If (Not Taken) Then
			Return(ID)
		EndIf
		ID = ID + 1
	Wend
End Function

Function ForceSetNPCID(n.NPCs, NewID%)
	Local n2.NPCs
	
	n\ID = NewID
	
	For n2.NPCs = Each NPCs
		If n2 <> n And n2\ID = NewID Then
			n2\ID = FindFreeNPCID()
		EndIf
	Next
End Function

Function ConsoleSpawnNPC(Name$, NPCState$ = "")
	Local n.NPCs
	Local ConsoleMsg$
	
	Select Name 
		Case "008", "008zombie", "008-1", "infectedhuman", "humaninfected", "scp008-1", "scp-008-1", "scp0081", "0081", "scp-0081"
			;[Block]
			n.NPCs = CreateNPC(NPCtype008_1, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 1.0
			ConsoleMsg = "SCP-008 infected human spawned."
			;[End Block]
		Case "049", "scp049", "scp-049", "plaguedoctor"
			;[Block]
			n.NPCs = CreateNPC(NPCtype049, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 1.0
			If Curr049 = Null Then Curr049 = n
			ConsoleMsg = "SCP-049 spawned."
			;[End Block]
		Case "049-2", "0492", "scp-049-2", "scp049-2", "049zombie", "curedhuman", "scp0492", "scp-0492"
			;[Block]
			n.NPCs = CreateNPC(NPCtype049_2, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 1.0
			ConsoleMsg = "SCP-049-2 spawned."
			;[End Block]
		Case "066", "scp066", "scp-066", "eric"
			;[Block]
			n.NPCs = CreateNPC(NPCtype066, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-066 spawned."
			;[End Block]
		Case "096", "scp096", "scp-096", "shyguy"
			;[Block]
			n.NPCs = CreateNPC(NPCtype096, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 5.0
			If Curr096 = Null Then Curr096 = n
			ConsoleMsg = "SCP-096 spawned."
			;[End Block]
		Case "106", "scp106", "scp-106", "larry", "oldman"
			;[Block]
			n.NPCs = CreateNPC(NPCtype106, EntityX(me\Collider), EntityY(me\Collider) - 0.5, EntityZ(me\Collider))
			n\State = -1.0
			ConsoleMsg = "SCP-106 spawned."
			;[End Block]
		Case "173", "scp173", "scp-173", "statue", "sculpture"
			;[Block]
			n.NPCs = CreateNPC(NPCtype173, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			Curr173 = n
			If Curr173\Idle = 3 Then Curr173\Idle = 0
			ConsoleMsg = "SCP-173 spawned."
			;[End Block]
		Case "372", "scp372", "scp-372", "pj", "jumper"
			;[Block]
			n.NPCs = CreateNPC(NPCtype372, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-372 spawned."
			;[End Block]
		Case "513-1", "5131", "scp513-1", "scp-513-1", "bll", "scp-5131", "scp5131"
			;[Block]
			n.NPCs = CreateNPC(NPCtype513_1, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-513-1 spawned."
			;[End Block]
		Case "860-2", "8602", "scp860-2", "scp-860-2", "forestmonster", "scp8602"
			;[Block]
			CreateConsoleMsg("SCP-860-2 cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "939", "scp939", "scp-939"
			CreateConsoleMsg("SCP-939 instances cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "966", "scp966", "scp-966", "sleepkiller"
			;[Block]
			n.NPCs = CreateNPC(NPCtype966, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-966 instance spawned."
			;[End Block]
		Case "1048-a", "scp1048-a", "scp-1048-a", "scp1048a", "scp-1048a", "earbear"
			;[Block]
			CreateConsoleMsg("SCP-1048-A cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "1048", "scp1048", "scp-1048", "scp-1048", "bear", "builderbear"
			;[Block]
			CreateConsoleMsg("SCP-1048 cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "1499-1", "14991", "scp-1499-1", "scp1499-1", "scp-14991", "scp14991"
			n.NPCs = CreateNPC(NPCtype1499_1, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-1499-1 instance spawned."
			;[End Block]
		Case "class-d", "classd", "d"
			;[Block]
			n.NPCs = CreateNPC(NPCtypeD, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "D-Class spawned."
			;[End Block]
		Case "guard"
			;[Block]
			n.NPCs = CreateNPC(NPCtypeGuard, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "Guard spawned."
			;[End Block]
		Case "mtf", "ntf"
			;[Block]
			n.NPCs = CreateNPC(NPCtypeMTF, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "MTF unit spawned."
			;[End Block]
		Case "apache", "helicopter"
			;[Block]
			n.NPCs = CreateNPC(NPCtypeApache, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "Apache spawned."
			;[End Block]
		Case "tentacle", "scp035tentacle", "scp-035tentacle", "scp-035-tentacle", "scp035-tentacle"
			;[Block]
			n.NPCs = CreateNPC(NPCtype035_Tentacle, EntityX(me\Collider), EntityY(me\Collider) - 0.12, EntityZ(me\Collider))
			ConsoleMsg = "SCP-035 tentacle spawned."
			;[End Block]
		Case "clerk", "woman"
			;[Block]
			n.NPCs = CreateNPC(NPCtypeClerk, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "Clerk spawned."
			;[End Block]
		Case "vehicle", "truck"
			;[Block]
			n.NPCs = CreateNPC(NPCtypeVehicle, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "Vehicle spawned."
			;[End Block]
		Default 
			;[Block]
			CreateConsoleMsg("NPC type not found.", 255, 0, 0) : Return
			;[End Block]
	End Select
	
	If n <> Null Then
		If NPCState <> "" Then n\State = Float(NPCState) : ConsoleMsg = ConsoleMsg + " (State = " + n\State + ")"
	EndIf
	
	CreateConsoleMsg(ConsoleMsg)
End Function

Function ManipulateNPCBones()
	Local n.NPCs, Bone%, Pvt%, BoneName$
	Local MaxValue#, MinValue#, Offset#, Smooth#
	Local i%
	Local ToValue#
	
	For n.NPCs = Each NPCs
		If n\ManipulateBone Then
			BoneName = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "bonename", 0)
			If BoneName <> ""
				Pvt = CreatePivot()
				Bone = FindChild(n\OBJ, BoneName)
				If (Not Bone) Then RuntimeError("ERROR: NPC bone " + Chr(34) + BoneName + Chr(34) + " does not exist.")
				PositionEntity(Pvt, EntityX(Bone, True), EntityY(Bone, True), EntityZ(Bone, True))
				Select n\ManipulationType
					Case 0 ; ~ Looking at player
						;[Block]
						For i = 1 To GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controller_max", 1)
							If GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i, 0) = "pitch"
								MaxValue = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_max", 2)
								MinValue = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_min", 2)
								Offset = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_offset", 2)
								If GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_inverse", 3)
									ToValue = (-DeltaPitch(Bone, Camera)) + Offset
								Else
									ToValue = DeltaPitch(Bone, Camera) + Offset
								EndIf
								Smooth = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_smoothing", 2)
								If Smooth > 0.0
									n\BonePitch = CurveAngle(ToValue, n\BonePitch, Smooth)
								Else
									n\BonePitch = ToValue
								EndIf
								n\BonePitch = ChangeAngleValueForCorrectBoneAssigning(n\BonePitch)
								n\BonePitch = Max(Min(n\BonePitch, MaxValue), MinValue)
							ElseIf GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis1", 0) = "yaw"
								MaxValue = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_max", 2)
								MinValue = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_min", 2)
								Offset = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_offset", 2)
								If GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_inverse", 3)
									ToValue = (-DeltaYaw(Bone, Camera)) + Offset
								Else
									ToValue = DeltaYaw(Bone, Camera) + Offset
								EndIf
								Smooth = GetNPCManipulationValue(n\NPCNameInSection, n\BoneToManipulate, "controlleraxis" + i + "_smoothing", 2)
								If Smooth > 0.0
									n\BoneYaw = CurveAngle(ToValue, n\BoneYaw, Smooth)
								Else
									n\BoneYaw = ToValue
								EndIf
								n\BoneYaw = ChangeAngleValueForCorrectBoneAssigning(n\BoneYaw)
								n\BoneYaw = Max(Min(n\BoneYaw, MaxValue), MinValue)
							EndIf
						Next
						RotateEntity(Bone, EntityPitch(Bone) + n\BonePitch, EntityYaw(Bone) + n\BoneYaw, EntityRoll(Bone) + n\BoneRoll)
						;[End Block]
				End Select
				FreeEntity(Pvt)
			EndIf
		EndIf
	Next
End Function

Function GetNPCManipulationValue$(NPC$, Bone$, Section$, ValueType% = 0)
	; ~ Valuetype determines what type of variable should the Output be returned
	; ~ 0: String
	; ~ 1: Int
	; ~ 2: Float
	; ~ 3: Boolean
	
	Local Value$ = GetINIString("Data\NPCBones.ini", NPC, Bone + "_" + Section)
	
	Select ValueType%
		Case 0
			;[Block]
			Return(Value)
			;[End Block]
		Case 1
			;[Block]
			Return(Int(Value))
			;[End Block]
		Case 2
			;[Block]
			Return(Float(Value))
			;[End Block]
		Case 3
			;[Block]
			If Value = "True" Lor Value = "1"
				Return(True)
			Else
				Return(False)
			EndIf
			;[End Block]
	End Select
End Function

Function NPCSpeedChange(n.NPCs)
	Select n\NPCtype
		Case NPCtype173, NPCtype106, NPCtype096, NPCtype049, NPCtype939, NPCtypeMTF
			Select SelectedDifficulty\OtherFactors
				Case NORMAL
					;[Block]
					n\Speed = n\Speed * 1.1
					;[End Block]
				Case HARD
					;[Block]
					n\Speed = n\Speed * 1.2
					;[End Block]
			End Select
	End Select
End Function

Function PlayerInReachableRoom(CanSpawnIn049Chamber% = False)
	Local RN$ = PlayerRoom\RoomTemplate\Name
	Local e.Events
	
	; ~ Player is in these rooms, returning false
	If RN = "pocketdimension" Lor RN = "gatea" Lor RN = "dimension1499" Lor RN = "room173intro" Then
		Return(False)
	EndIf
	; ~ Player is at Gate B and is at the surface, returning false
	If RN = "gateb" And EntityY(me\Collider) > 1040.0 * RoomScale Then
		Return(False)
	EndIf
	; ~ Player is in SCP-860-1's test room and inside the forest, returning false
	If RN = "room860" Then
		For e.Events = Each Events
			If e\EventID = e_room860 Then
				If e\EventState = 1.0 Then
					Return(False)
				EndIf
				Exit
			EndIf
		Next	
	EndIf
	
	If (Not CanSpawnIn049Chamber) Then
		If (Not SelectedDifficulty\AggressiveNPCs) Then
			If RN = "room049" And EntityY(me\Collider) =< -2848.0 * RoomScale Then
				Return(False)
			EndIf
		EndIf
	EndIf
	; ~ Return true, this means player is in reachable room
	Return(True)
End Function

Function CheckForNPCInFacility(n.NPCs)
	; ~ False (= 0): NPC is not in facility (mostly meant for "dimension1499")
	; ~ True (= 1): NPC is in facility
	; ~ 2: NPC is in tunnels (Maintenance Tunnels / SCP-049 tunnels / SCP-939 storage room, etc...)
	
	If EntityY(n\Collider) > 100.0
		Return(False)
	EndIf
	If EntityY(n\Collider) < -10.0
		Return(2)
	EndIf
	If EntityY(n\Collider) > 7.0 And EntityY(n\Collider) =< 100.0
		Return(2)
	EndIf
	
	Return(True)
End Function

Function SetNPCFrame(n.NPCs, Frame#)
	If Abs(n\Frame - Frame) < 0.001 Then Return
	
	SetAnimTime(n\OBJ, Frame)
	
	n\Frame = Frame
End Function

Function AnimateNPC(n.NPCs, FirstFrame#, LastFrame#, Speed#, Loop% = True)
	Local NewTime#, Temp%
	
	If Speed > 0.0 Then 
		NewTime = Max(Min(n\Frame + Speed * fps\FPSFactor[0], LastFrame), FirstFrame)
		
		If Loop And NewTime >= LastFrame Then
			NewTime = FirstFrame
		EndIf
	Else
		If FirstFrame < LastFrame Then
			Temp = FirstFrame
			FirstFrame = LastFrame
			LastFrame = Temp
		EndIf
		
		If Loop Then
			NewTime = n\Frame + Speed * fps\FPSFactor[0]
			
			If NewTime < LastFrame Then 
				NewTime = FirstFrame
			ElseIf NewTime > FirstFrame 
				NewTime = LastFrame
			EndIf
		Else
			NewTime = Max(Min(n\Frame + Speed * fps\FPSFactor[0], FirstFrame), LastFrame)
		EndIf
	EndIf
	SetNPCFrame(n, NewTime)
End Function

Function Animate2#(Entity%, Curr#, FirstFrame%, LastFrame%, Speed#, Loop% = True)
	Local NewTime#, Temp%
	
	If Speed > 0.0 Then 
		NewTime = Max(Min(Curr + Speed * fps\FPSFactor[0], LastFrame), FirstFrame)
		
		If Loop Then
			If NewTime >= LastFrame Then 
				NewTime = FirstFrame
			EndIf
		EndIf
	Else
		If FirstFrame < LastFrame Then
			Temp = FirstFrame
			FirstFrame = LastFrame
			LastFrame = Temp
		EndIf
		
		If Loop Then
			NewTime = Curr + Speed * fps\FPSFactor[0]
			
			If NewTime < LastFrame Then NewTime = FirstFrame
			If NewTime > FirstFrame Then NewTime = LastFrame
		Else
			NewTime = Max(Min(Curr + Speed * fps\FPSFactor[0], FirstFrame), LastFrame)
		EndIf
	EndIf
	
	SetAnimTime(Entity, NewTime)
	
	Return(NewTime)
End Function 

Function FinishWalking(n.NPCs, StartFrame#, EndFrame#, Speed#)
	Local CenterFrame#
	
	If n <> Null
		CenterFrame = (EndFrame - StartFrame) / 2.0
		If n\Frame >= CenterFrame
			AnimateNPC(n, StartFrame, EndFrame, Speed, False)
		Else
			AnimateNPC(n, EndFrame, StartFrame, -Speed, False)
		EndIf
	EndIf
End Function

Function ChangeNPCTextureID(n.NPCs, TextureID%) ; ~ Works only for Class D model
	Local Temp#
	
	If n = Null Then
		CreateConsoleMsg("Tried to change the texture of an invalid NPC!")
		If opt\CanOpenConsole And opt\ConsoleOpening Then ConsoleOpen = True
		Return
	EndIf
	
	n\TextureID = TextureID + 1
	If n\OBJ <> 0 Then FreeEntity(n\OBJ)
	n\OBJ = CopyEntity(o\DTextures[TextureID])
	
	Temp = GetINIFloat(NPCsFile, "Class D", "Scale") / MeshWidth(n\OBJ)
	ScaleEntity(n\OBJ, Temp, Temp, Temp)
	MeshCullBox(n\OBJ, -MeshWidth(n\OBJ), -MeshHeight(n\OBJ), -MeshDepth(n\OBJ), MeshWidth(n\OBJ) * 2.0, MeshHeight(n\OBJ) * 2.0, MeshDepth(n\OBJ) * 2.0)
	
	SetNPCFrame(n, n\Frame)
End Function

;~IDEal Editor Parameters:
;~B#17F#1210#1357#135B#14FD#1619#17E8#1843
;~C#Blitz3D