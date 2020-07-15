Graphics3D(640, 480, 0, 2)

AppTitle("Convert SCP-CB rooms (.b3d generated by 3DWS) to .rmesh")

Const HIT_MAP% = 1
Const HIT_PLAYER% = 2

Collisions(HIT_PLAYER, HIT_MAP, 2, 2)

Function StripPath$(File$) 
	Local Name$ = "", i%, mi$
	
	If Len(File) > 0 
		For i = Len(File) To 1 Step - 1 
			
			mi = Mid(File, i, 1) 
			If mi = "\" Lor mi = "/" Then Return(Name)
			
			Name$ = mi + Name
		Next 
	EndIf 
	
	Return(Name)
End Function 

Function StripFilename$(File)
	Local mi$ = "", LastSlash% = 0, i%
	
	If Len(File) > 0 Then
		For i = 1 To Len(File)
			mi = Mid(File, i, 1)
			If mi = "\" Lor mi = "/" Then
				LastSlash = i
			EndIf
		Next
	EndIf
	
	Return(Left(File, LastSlash))
End Function

Function Piece$(s$, Entry%, Char$ = " ")
	Local n%, p%, a$
	
	While Instr(s, Char + Char)
		s = Replace(s, Char + Char,Char)
	Wend
	For n = 1 To Entry - 1
		p = Instr(s, Char)
		s = Right(s, Len(s) - p)
	Next
	p = Instr(s, Char)
	If p < 1 Then
		a = s
	Else
		a = Left(s, p - 1)
	EndIf
	Return(a)
End Function

Function KeyValue$(Entity%, Key$, DefaultValue$ = "")
	Local p%, Value$, Properties$, TestKey$, Test$
	
	Properties = EntityName(Entity)
	Properties = Replace(Properties, Chr(13), "")
	Key = Lower(Key)
	Repeat
		p = Instr(Properties, Chr(10))
		If p Then Test = (Left(Properties, p - 1)) Else Test = Properties
		TestKey = Piece(Test, 1, "=")
		TestKey = Trim(TestKey)
		TestKey = Replace(TestKey, Chr(34), "")
		TestKey = Lower(TestKey)
		If TestKey = Key Then
			Value = Piece(Test, 2, "=")
			Value = Trim(Value)
			Value = Replace(Value, Chr(34), "")
			Return(Value)
		EndIf
		If (Not p) Then Return(DefaultValue)
		Properties = Right(Properties, Len(Properties) - p)
	Forever 
End Function

Function IsAlpha%(Tex%) ; ~ Detect transparency in textures
	Local Temp1s$ = StripPath(TextureName(Tex))
	Local Temp1i%
	Local x%, y%
	
	If Instr(Temp1s, ".png") <> 0 Lor Instr(Temp1s, ".tga") <> 0 Lor Instr(Temp1s, ".tpic") <> 0 Then ; ~ Texture is PNG or TARGA
		LockBuffer(TextureBuffer(Tex))
		For x = 0 To TextureWidth(Tex) - 1
			For y = 0 To TextureHeight(Tex) - 1
				Temp1i = ReadPixelFast(x, y, TextureBuffer(Tex))
				Temp1i = Temp1i Shr 24
				If Temp1i < 255 Then
					UnlockBuffer(TextureBuffer(Tex))
					Return(3) ; ~ Texture has transparency
				EndIf
			Next
		Next
		UnlockBuffer(TextureBuffer(Tex))
		Return(1) ; ~ Texture is opaque
	ElseIf Instr(Temp1s, "_lm") <> 0 Then ; ~ Texture is a lightmap
		Return(2)
	EndIf
	Return(1) ; ~ Texture is opaque
End Function

Function SaveRoomMesh(BaseMesh%, FileName$) ; ~ Base mesh should be a 3D World Studio mesh
	Local Node%, ClassName$, c%, i%, j%, z%
	Local Surf%, Brush%, Tex%, TexName$
	Local Temp1i%
	Local TempMesh% = BaseMesh
	Local f% = WriteFile(FileName)
	Local DrawnMesh% = CreateMesh()
	Local HiddenMesh% = CreateMesh()
	Local TriggerBoxAmount% = 0
	Local TriggerBox%[128]
	Local TriggerBoxName$[128]
	
	For c = 1 To CountChildren(TempMesh)
		Node = GetChild(TempMesh, c)	
		ClassName = Lower(KeyValue(Node, "classname"))
		
		Select ClassName
			Case "mesh"
				;[Block]
				ScaleMesh(Node, EntityScaleX(Node), EntityScaleY(Node), EntityScaleZ(Node))
				RotateMesh(Node, EntityPitch(Node), EntityYaw(Node), EntityRoll(Node))
				PositionMesh(Node, EntityX(Node), EntityY(Node), EntityZ(Node))
				AddMesh(Node, DrawnMesh)
				;[End Block]
			Case "brush"
				;[Block]
				RotateMesh(Node, EntityPitch(Node), EntityYaw(Node), EntityRoll(Node))
				PositionMesh(Node, EntityX(Node), EntityY(Node), EntityZ(Node))
				AddMesh(Node, DrawnMesh)
				;[End Block]
			Case "field_hit"
				;[Block]
				RotateMesh(Node, EntityPitch(Node), EntityYaw(Node), EntityRoll(Node))
				PositionMesh(Node, EntityX(Node), EntityY(Node), EntityZ(Node))
				AddMesh(Node, HiddenMesh)
				;[End Block]
			Case "trigger"
				;[Block]
				TriggerBox[TriggerBoxAmount] = CreateMesh()
				RotateMesh(Node, EntityPitch(Node), EntityYaw(Node), EntityRoll(Node))
				PositionMesh(Node, EntityX(Node), EntityY(Node), EntityZ(Node))
				AddMesh(Node, TriggerBox[TriggerBoxAmount])
				TriggerBoxName[TriggerBoxAmount] = String(KeyValue(Node, "event", "event"), 1)
				TriggerBoxAmount = TriggerBoxAmount + 1
		End Select
	Next
	
	If TriggerBoxAmount = 0
		WriteString(f, "RoomMesh")
	Else
		WriteString(f, "RoomMesh.HasTriggerBox")
	EndIf
	
	WriteInt(f, CountSurfaces(DrawnMesh))
	For i = 1 To CountSurfaces(DrawnMesh)
		Surf = GetSurface(DrawnMesh, i)
		Brush = GetSurfaceBrush(Surf)
		
		Tex = 0
		Tex = GetBrushTexture(Brush, 0)
		If Tex <> 0 Then
			WriteByte(f, IsAlpha(Tex))
			TexName = TextureName(Tex)
			WriteString(f, StripPath(TexName))
			FreeTexture(Tex)
		Else
			WriteByte(f, 0)
		EndIf
		
		Tex = 0
		Tex = GetBrushTexture(Brush, 1)
		If Tex <> 0 Then
			WriteByte(f, IsAlpha(Tex))
			TexName = TextureName(Tex)
			WriteString(f, StripPath(TexName))
			FreeTexture(Tex)
		Else
			WriteByte(f, 0)
		EndIf
		
		FreeBrush(Brush)
		
		WriteInt(f, CountVertices(Surf))
		For j = 0 To CountVertices(Surf) - 1
			; ~ World coordinates
			WriteFloat(f, VertexX(Surf, j))
			WriteFloat(f, VertexY(Surf, j))
			WriteFloat(f, VertexZ(Surf, j))
			
			; ~ Texture coordinates
			WriteFloat(f, VertexU(Surf, j, 0))
			WriteFloat(f, VertexV(Surf, j, 0))
			
			WriteFloat(f, VertexU(Surf, j, 1))
			WriteFloat(f, VertexV(Surf, j, 1))
			
			; ~ Colors
			WriteByte(f, VertexRed(Surf, j))
			WriteByte(f, VertexGreen(Surf, j))
			WriteByte(f, VertexBlue(Surf, j))
		Next
		
		WriteInt(f, CountTriangles(Surf))
		For j = 0 To CountTriangles(Surf) - 1
			WriteInt(f, TriangleVertex(Surf, j, 0))
			WriteInt(f, TriangleVertex(Surf, j, 1))
			WriteInt(f, TriangleVertex(Surf, j, 2))
		Next
	Next
	
	WriteInt(f, CountSurfaces(HiddenMesh))
	For i = 1 To CountSurfaces(HiddenMesh)
		Surf = GetSurface(HiddenMesh, i)
		WriteInt(f, CountVertices(Surf))
		For j = 0 To CountVertices(Surf) - 1
			; ~ World coords
			WriteFloat(f, VertexX(Surf, j))
			WriteFloat(f, VertexY(Surf, j))
			WriteFloat(f, VertexZ(Surf, j))
		Next
		
		WriteInt(f, CountTriangles(Surf))
		For j = 0 To CountTriangles(Surf) - 1
			WriteInt(f, TriangleVertex(Surf, j, 0))
			WriteInt(f, TriangleVertex(Surf, j, 1))
			WriteInt(f, TriangleVertex(Surf, j, 2))
		Next
	Next
	
	If TriggerBoxAmount > 0 Then
		WriteInt(f, TriggerBoxAmount)
		For z = 0 To TriggerBoxAmount - 1
			WriteInt(f, CountSurfaces(TriggerBox[z]))
			For i = 1 To CountSurfaces(TriggerBox[z])
				Surf = GetSurface(TriggerBox[z],i)
				WriteInt(f, CountVertices(Surf))
				For j = 0 To CountVertices(Surf) - 1
					; ~ World coords
					WriteFloat(f, VertexX(Surf, j))
					WriteFloat(f, VertexY(Surf, j))
					WriteFloat(f, VertexZ(Surf, j))
				Next
				
				WriteInt(f, CountTriangles(Surf))
				For j = 0 To CountTriangles(Surf) - 1
					WriteInt(f, TriangleVertex(Surf, j, 0))
					WriteInt(f, TriangleVertex(Surf, j, 1))
					WriteInt(f, TriangleVertex(Surf, j, 2))
				Next
			Next
			WriteString(f, TriggerBoxName[z])
		Next
	EndIf
	
	Temp1i = 0
	
	For c = 1 To CountChildren(TempMesh)
		Node = GetChild(TempMesh, c)	
		ClassName = Lower(KeyValue(Node, "classname"))
		
		Select ClassName
			Case "screen", "waypoint", "light", "spotlight", "soundemitter", "playerstart", "model"
				;[Block]
				Temp1i = Temp1i + 1
				;[End Block]
		End Select
	Next
	
	WriteInt(f, Temp1i)
	
	For c = 1 To CountChildren(TempMesh)
		Node = GetChild(TempMesh, c)	
		ClassName = Lower(KeyValue(Node, "classname"))
		
		Select ClassName
			Case "screen"
				;[Block]
				WriteString(f, ClassName)
				
				WriteFloat(f, EntityX(Node))
				WriteFloat(f, EntityY(Node))
				WriteFloat(f, EntityZ(Node))
				
				WriteString(f, KeyValue(Node, "imgpath", ""))
				;[End Block]
			Case "waypoint"
				;[Block]
				WriteString(f, ClassName)
				
				WriteFloat(f, EntityX(Node))
				WriteFloat(f, EntityY(Node))
				WriteFloat(f, EntityZ(Node))
				;[End Block]
			Case "light"
				;[Block]
				WriteString(f, ClassName)
				
				WriteFloat(f, EntityX(Node))
				WriteFloat(f, EntityY(Node))
				WriteFloat(f, EntityZ(Node))
				
				WriteFloat(f, Float(KeyValue(Node, "range", "1")))
				WriteString(f, KeyValue(Node, "color", "255 255 255"))
				WriteFloat(f, Float(KeyValue(Node, "intensity", "1.0")))
				;[End Block]
			Case "spotlight"
				;[Block]
				WriteString(f, ClassName)
				
				WriteFloat(f, EntityX(Node))
				WriteFloat(f, EntityY(Node))
				WriteFloat(f, EntityZ(Node))
				
				WriteFloat(f, Float(KeyValue(Node, "range", "1")))
				WriteString(f, KeyValue(Node, "color", "255 255 255"))
				WriteFloat(f, Float(KeyValue(Node, "intensity", "1.0")))
				WriteString(f, KeyValue(Node, "angles", "0 0 0"))
				
				WriteInt(f, Int(KeyValue(Node, "innerconeangle", "")))
				WriteInt(f, Int(KeyValue(Node, "outerconeangle", "")))
				;[End Block]
			Case "soundemitter"
				;[Block]
				WriteString(f, ClassName)
				
				WriteFloat(f, EntityX(Node))
				WriteFloat(f, EntityY(Node))
				WriteFloat(f, EntityZ(Node))
				
				WriteInt(f, Int(KeyValue(Node, "sound", "0")))
				WriteFloat(f, Float(KeyValue(Node, "range", "1")))
				;[End Block]
			Case "playerstart"
				;[Block]
				WriteString(f, ClassName)
				
				WriteFloat(f, EntityX(Node))
				WriteFloat(f, EntityY(Node))
				WriteFloat(f, EntityZ(Node))
				
				WriteString(f, KeyValue(Node, "angles", "0 0 0"))
				;[End Block]
			Case "model"
				;[Block]
				WriteString(f, ClassName)
				
				WriteString(f, KeyValue(Node, "file", ""))
				
				WriteFloat(f, EntityX(Node))
				WriteFloat(f, EntityY(Node))
				WriteFloat(f, EntityZ(Node))
				
				WriteFloat(f, EntityPitch(Node))
				WriteFloat(f, EntityYaw(Node))
				WriteFloat(f, EntityRoll(Node))
				
				WriteFloat(f, EntityScaleX(Node))
				WriteFloat(f, EntityScaleY(Node))
				WriteFloat(f, EntityScaleZ(Node))
				;[End Block]
		End Select
	Next
	
	WriteString(f, "EOF")
	
	CloseFile(f)
	
	FreeEntity(DrawnMesh)
	FreeEntity(HiddenMesh)
End Function

Function GetINIString$(File$, Section$, Parameter$)
	Local TemporaryString$ = ""
	Local f% = ReadFile(File)
	
	While Not Eof(f)
		If ReadLine(f) = "[" + Section + "]" Then
			Repeat 
				TemporaryString = ReadLine(f)
				If Trim(Left(TemporaryString, Max(Instr(TemporaryString, "=") - 1, 0))) = Parameter Then
					CloseFile(f)
					Return(Trim(Right(TemporaryString, Len(TemporaryString) - Instr(TemporaryString, "="))))
				EndIf
			Until Left(TemporaryString, 1) = "[" Lor Eof(f)
			CloseFile(f)
			Return("")
		EndIf
	Wend
	
	CloseFile(f)
End Function

Function GetINIInt%(File$, Section$, Parameter$)
	Local Strtemp$ = Lower(GetINIString(File, Section, Parameter))
	
	Select Strtemp
		Case "true"
			;[Block]
			Return(1)
			;[End Block]
		Case "false"
			;[Block]
			Return(0)
			;[End Block]
		Default
			;[Block]
			Return(Int(Strtemp))
			;[End Block]
	End Select
	Return 
End Function

Function GetINIFloat#(File$, Section$, Parameter$)
	Return(GetINIString(File, Section, Parameter))
End Function

Function PutINIValue%(INI_sAppName$, INI_sSection$, INI_sKey$, INI_sValue$)
	; ~ Returns: True (Success) or False (Failed)
	INI_sSection = "[" + Trim(INI_sSection) + "]"
	INI_sUpperSection$ = Upper(INI_sSection)
	INI_sKey = Trim(INI_sKey)
	INI_sValue = Trim(INI_sValue)
	INI_sFilename$ = CurrentDir() + "\"  + INI_sAppName
	
    ; ~ Retrieve the INI data (if it exists)
	INI_sContents$ = INI_FileToString(INI_sFilename)
	
    ; ~ (Re)Create the INI file updating / adding the SECTION, KEY and VALUE
	INI_bWrittenKey = False
	INI_bSectionFound = False
	INI_sCurrentSection$ = ""
	
	INI_lFileHandle = WriteFile(INI_sFilename)
	If INI_lFileHandle = 0 Then Return(False) ; ~ Create file failed!
	
	INI_lOldPos = 1
	INI_lPos = Instr(INI_sContents, Chr(0))
	
	While INI_lPos <> 0
		INI_sTemp$ = Trim(Mid(INI_sContents, INI_lOldPos, (INI_lPos - INI_lOldPos)))
		
		If INI_sTemp <> "" Then
			If Left(INI_sTemp, 1) = "[" And Right(INI_sTemp, 1) = "]" Then
				; ~ Process SECTION
				If INI_sCurrentSection = INI_sUpperSection And INI_bWrittenKey = False Then
					INI_bWrittenKey = INI_CreateKey(INI_lFileHandle, INI_sKey, INI_sValue)
				EndIf
				INI_sCurrentSection = Upper(INI_CreateSection(INI_lFileHandle, INI_sTemp))
				If INI_sCurrentSection = INI_sUpperSection Then INI_bSectionFound = True
			Else
				lEqualsPos% = Instr(INI_sTemp, "=")
				If lEqualsPos <> 0 Then
					If INI_sCurrentSection = INI_sUpperSection And Upper(Trim(Left(INI_sTemp, (lEqualsPos - 1)))) = Upper(INI_sKey) Then
						If INI_sValue <> "" Then INI_CreateKey(INI_lFileHandle, INI_sKey, INI_sValue)
						INI_bWrittenKey = True
					Else
						WriteLine(INI_lFileHandle, INI_sTemp)
					EndIf
				EndIf
			EndIf
		EndIf
		
		; ~ Move through the INI file...
		INI_lOldPos = INI_lPos + 1
		INI_lPos = Instr(INI_sContents, Chr(0), INI_lOldPos)
	Wend
	
	; ~ KEY wasn't found in the INI file - Append a new SECTION if required and create our KEY = VALUE line
	If INI_bWrittenKey = False Then
		If INI_bSectionFound = False Then INI_CreateSection(INI_lFileHandle, INI_sSection)
		INI_CreateKey(INI_lFileHandle, INI_sKey, INI_sValue)
	EndIf
	
	CloseFile(INI_lFileHandle)
	
	Return(True) ; ~ Success
End Function

Function INI_FileToString$(INI_sFilename$)
	INI_sString$ = ""
	INI_lFileHandle = ReadFile(INI_sFilename)
	If INI_lFileHandle <> 0 Then
		While (Not(Eof(INI_lFileHandle)))
			INI_sString = INI_sString + ReadLine(INI_lFileHandle) + Chr(0)
		Wend
		CloseFile(INI_lFileHandle)
	EndIf
	Return(INI_sString)
End Function

Function INI_CreateSection$(INI_lFileHandle%, INI_sNewSection$)
	If FilePos(INI_lFileHandle) <> 0 Then WriteLine(INI_lFileHandle, "") ; ~ Blank line between sections
	WriteLine(INI_lFileHandle, INI_sNewSection)
	Return(INI_sNewSection)
End Function

Function INI_CreateKey%(INI_lFileHandle%, INI_sKey$, INI_sValue$)
	WriteLine(INI_lFileHandle, INI_sKey + "=" + INI_sValue)
	Return(True)
End Function

Local State% = 0

SetBuffer(BackBuffer())
ClsColor(0, 0, 0)
Cls()
Color(255, 255, 255)
Text(5, 5, "Press a key:")
Text(5, 25, "1 - Convert B3D (generated by 3D World Studio) to RMESH")
Text(5, 45, "2 - Convert a specific B3D (generated by 3D World Studio) to RMESH")
Text(5, 65, "3 - Reset rooms.ini to use B3D meshes")
Text(5, 85, "ESC - Close without doing anything")
Flip()

While (Not KeyHit(1))
	If KeyHit(2) Lor KeyHit(79) Then State = 1 : Exit
	If KeyHit(3) Lor KeyHit(80) Then State = 2 : Exit
	If KeyHit(4) Lor KeyHit(81) Then State = 3 : Exit
Wend

Local Stri$, TemporaryString$, f%

Type INIConvert
	Field File$
	Field Section$
	Field Key$
	Field Value$
End Type

Local ic.INIConvert, Mesh%

If State = 1 Then ; ~ Convert B3D to Rmesh
	If FileSize("Data\rooms_b3d.ini") = 0 Then
		CopyFile("Data\rooms.ini", "Data\rooms_b3d.ini")
	EndIf
	
	f = ReadFile("Data\rooms.ini")
	
	While (Not Eof(f))
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString, 1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			
			If TemporaryString <> "room ambience" Then
				Stri = GetINIString("Data\rooms.ini", TemporaryString, "Mesh Path")
				
				Mesh = LoadAnimMesh(Stri)
				SaveRoomMesh(Mesh, Replace(Stri, ".b3d", ".rmesh"))
				
				Cls()
				Text(5, 5, "Converted " + Chr(34) + Stri + Chr(34))
				Flip()
				
				ic.INIConvert = New INIConvert
				ic\File = "Data\rooms.ini"
				ic\Section = TemporaryString
				ic\Key = "Mesh Path"
				ic\Value = Replace(Stri, ".b3d", ".rmesh")
			EndIf
		EndIf
	Wend
	
	For ic.INIConvert = Each INIConvert
		PutINIValue(ic\File, ic\Section, ic\Key, ic\Value)
	Next
	
	Cls()
	Text(5, 5, "Conversion complete")
	Flip()
	Delay(1000)
	
	CloseFile(f)
ElseIf State = 2
	Cls()
	Flip()
	FlushKeys()
	
	Stri = Input("Path for the room to be converted: ")
	Mesh = LoadAnimMesh(Stri)
	SaveRoomMesh(Mesh, Replace(Stri, ".b3d", ".rmesh"))
	Cls()
	Text(5, 5, "Conversion of " + Stri + " complete")
	Flip()
	Delay(1000)
ElseIf State = 3
	f = ReadFile("Data\rooms.ini")
	
	While (Not Eof(f))
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString, 1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			
			If TemporaryString <> "room ambience" Then
				Stri = GetINIString("Data\rooms.ini", TemporaryString, "Mesh Path")
				
				ic.INIConvert = New INIConvert
				ic\File = "Data\rooms.ini"
				ic\Section = TemporaryString
				ic\Key = "Mesh Path"
				ic\Value = Replace(Stri, ".rmesh", ".b3d")
			EndIf
		EndIf
	Wend
	
	For ic.INIConvert = Each INIConvert
		PutINIValue(ic\File, ic\Section, ic\Key, ic\Value)
	Next
	
	Cls()
	Text(5, 5, "Reset complete")
	Flip()
	Delay(1000)
	
	CloseFile(f)
EndIf

;~IDEal Editor Parameters:
;~C#Blitz3D