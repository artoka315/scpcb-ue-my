xof 0303txt 0032
template XSkinMeshHeader {
 <3cf169ce-ff7c-44ab-93c0-f78f62d172e2>
 WORD nMaxSkinWeightsPerVertex;
 WORD nMaxSkinWeightsPerFace;
 WORD nBones;
}

template VertexDuplicationIndices {
 <b8d65549-d7c9-4995-89cf-53a9a8b031e3>
 DWORD nIndices;
 DWORD nOriginalVertices;
 array DWORD indices[nIndices];
}

template SkinWeights {
 <6f0d123b-bad2-4167-a0d0-80224f25fabb>
 STRING transformNodeName;
 DWORD nWeights;
 array DWORD vertexIndices[nWeights];
 array FLOAT weights[nWeights];
 Matrix4x4 matrixOffset;
}

template AnimTicksPerSecond {
 <9e415a43-7ba6-4a73-8743-b73d47e88476>
 DWORD AnimTicksPerSecond;
}


AnimTicksPerSecond {
 24;
}

Frame Scene_Root {
 

 FrameTransformMatrix {
  1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,0.000000,0.000000,0.000000,1.000000;;
 }

 Frame body_bmp {
  

  FrameTransformMatrix {
   1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,0.000000,0.000000,0.000000,1.000000;;
  }

  Mesh body_bmp {
   162;
   8.703922;3.109210;-1.740971;,
   6.683921;0.843268;-3.881226;,
   7.503925;3.120053;7.024830;,
   4.613823;0.908292;7.838928;,
   5.073925;0.761972;0.703731;,
   0.303822;0.164494;8.470230;,
   0.323921;0.342013;0.714630;,
   -4.696080;0.750012;0.753731;,
   -5.906079;0.817282;-4.049498;,
   -4.406178;0.905612;7.820431;,
   -6.946075;3.116052;7.024830;,
   -6.056076;3.040332;8.004929;,
   -4.856075;4.452091;7.182331;,
   -2.086078;4.884293;7.831532;,
   0.343922;5.440052;7.030529;,
   2.753922;4.884293;7.821529;,
   -7.067677;0.957603;-7.250526;,
   -7.145077;1.406628;-4.491594;,
   -6.310781;0.732006;-8.311712;,
   -8.256077;3.117212;-1.711071;,
   -6.616078;4.095494;-3.895821;,
   0.343922;5.212012;2.662127;,
   5.343922;4.336093;7.202530;,
   2.753922;4.884293;7.821529;,
   6.803925;3.052332;7.994930;,
   7.067019;1.517836;-9.185999;,
   7.171618;2.309186;-9.418514;,
   7.032123;1.384981;-9.826269;,
   4.546618;2.224488;-9.863018;,
   7.561122;2.647395;-8.581539;,
   4.154224;2.551503;-9.171188;,
   4.507823;3.266761;-7.883969;,
   0.373924;3.553792;-7.932200;,
   0.333923;4.512810;-4.548244;,
   -4.206082;3.267042;-7.880655;,
   -7.844179;2.799150;-7.918440;,
   -7.684080;3.585471;-4.476056;,
   -0.416077;0.710945;-4.057261;,
   -1.776078;0.057813;0.205231;,
   -0.736077;0.497852;0.294330;,
   -5.566079;1.649812;0.191929;,
   -5.846077;0.689812;0.103931;,
   -6.216076;0.381493;-0.835468;,
   -1.546078;0.299825;-3.314145;,
   -6.176075;0.649193;-3.501885;,
   -6.106076;1.126005;-4.259167;,
   6.153923;1.357854;0.312530;,
   6.403923;1.221881;-4.116078;,
   6.703922;0.477534;-0.705670;,
   6.663921;0.758620;-3.372000;,
   0.903923;0.722331;-3.896107;,
   1.993923;0.241362;-3.158385;,
   2.223922;-0.046146;0.315430;,
   6.283924;0.689893;0.363930;,
   1.263923;0.585852;0.274132;,
   5.863922;2.557330;6.977230;,
   6.503921;2.459811;2.057430;,
   8.113823;1.585094;6.089130;,
   8.353924;1.484254;3.409328;,
   1.483925;0.707852;2.090832;,
   2.893822;-0.111787;3.252430;,
   2.753822;0.113453;7.252029;,
   7.323822;1.697453;7.268929;,
   1.603825;0.757492;7.450729;,
   -4.466076;4.021292;6.774429;,
   -5.396077;4.295212;0.093929;,
   -5.266075;5.372971;5.721828;,
   -5.456078;5.523571;1.241531;,
   -1.586079;4.927173;-0.017369;,
   -2.836079;5.891572;1.270828;,
   -2.196075;5.661371;6.981232;,
   -4.656078;5.149332;6.922230;,
   -1.186077;4.677292;6.833130;,
   1.973922;5.046973;-0.747571;,
   2.883923;5.461133;6.251628;,
   1.813923;4.849091;6.122829;,
   5.403923;4.025091;6.084430;,
   5.303925;4.901093;6.202728;,
   5.953922;5.192730;4.982127;,
   3.593921;5.791333;0.511028;,
   6.193923;5.411332;0.491729;,
   6.203922;4.423010;-0.626370;,
   -6.435979;1.375821;-9.853970;,
   -6.425481;0.735919;-8.970522;,
   -3.430081;1.289832;-10.150780;,
   -2.773079;0.586212;-8.735973;,
   -1.626076;0.262773;-7.377334;,
   0.313922;0.242655;-6.472786;,
   0.403922;2.533959;-9.986967;,
   -3.320783;2.601528;-9.258907;,
   -2.646077;2.191784;-10.045813;,
   -3.359181;1.379453;-9.713764;,
   -3.392880;2.199984;-9.793604;,
   -3.430081;1.289832;-10.150780;,
   -6.557080;2.103664;-9.723446;,
   -6.435979;1.375821;-9.853970;,
   -7.006078;2.524074;-8.745995;,
   -6.319882;1.516922;-9.205538;,
   -6.796078;2.247972;2.457828;,
   -5.746075;2.825531;7.486729;,
   -7.436177;2.361653;7.937629;,
   -5.336178;0.625772;8.281029;,
   -0.606178;0.889732;8.210529;,
   -2.116176;0.165733;8.191929;,
   -2.336178;0.056093;2.952129;,
   -5.786179;0.492132;2.981236;,
   -7.646077;2.016133;2.978328;,
   7.074420;0.743587;-8.241999;,
   3.901821;0.519677;-9.156734;,
   2.273921;0.256559;-7.437353;,
   7.128917;0.751885;-9.049467;,
   7.032123;1.384981;-9.826269;,
   7.067019;1.517836;-9.185999;,
   7.696521;0.955753;-7.259231;,
   7.793922;1.313795;-4.575732;,
   6.793922;4.107969;-3.837131;,
   7.503925;3.120053;7.024830;,
   8.703922;3.109210;-1.740971;,
   8.051022;3.553793;-4.609694;,
   7.465770;2.896084;-7.585539;,
   3.163921;2.210498;-10.018404;,
   4.833923;4.452450;4.113628;,
   5.273922;4.904532;4.392730;,
   5.563922;4.790613;-1.827069;,
   5.983921;5.294693;-1.568070;,
   2.843922;5.292492;4.211928;,
   3.563923;5.682651;-1.708771;,
   2.423923;4.788413;3.972930;,
   3.133922;5.186573;-1.977871;,
   3.743824;1.004652;9.118729;,
   0.253823;0.624652;9.119431;,
   -2.956177;0.980693;9.138729;,
   -6.319882;1.516922;-9.205538;,
   -0.926079;0.847852;2.110531;,
   4.652820;1.108515;-9.595602;,
   4.652820;1.108515;-9.595602;,
   3.901821;0.519677;-9.156734;,
   4.652820;1.108515;-9.595602;,
   2.273921;0.256559;-7.437353;,
   -2.546380;0.838537;-8.549459;,
   -1.626076;0.262773;-7.377334;,
   -2.773079;0.586212;-8.735973;,
   -3.359181;1.379453;-9.713764;,
   6.803925;3.052332;7.994930;,
   5.563922;4.790613;-1.827069;,
   3.133922;5.186573;-1.977871;,
   4.833923;4.452450;4.113628;,
   2.423923;4.788413;3.972930;,
   -3.358182;0.643610;-8.358353;,
   -3.465978;0.345843;-8.851011;,
   -3.281781;0.620541;-8.112894;,
   -3.379582;0.322627;-8.606234;,
   5.113922;0.341222;-5.287066;,
   6.123919;0.311713;-7.101378;,
   3.353923;0.149700;-6.125469;,
   4.363922;0.120170;-7.939878;,
   5.003921;0.790320;-4.422138;,
   2.673923;0.537369;-5.533839;,
   6.353923;0.752667;-6.828084;,
   4.013923;0.499683;-7.939780;,
   -3.359181;1.379453;-9.713764;,
   -3.359181;1.379453;-9.713764;;
   200;
   3;0,2,1;,
   3;2,3,1;,
   3;1,3,4;,
   3;3,5,4;,
   3;4,5,6;,
   3;5,7,6;,
   3;6,7,8;,
   3;7,9,8;,
   3;8,9,10;,
   3;9,11,10;,
   3;10,11,12;,
   3;11,13,12;,
   3;12,13,14;,
   3;13,15,14;,
   3;16,18,17;,
   3;18,8,17;,
   3;17,8,19;,
   3;8,10,19;,
   3;19,10,20;,
   3;10,12,20;,
   3;20,12,21;,
   3;12,14,21;,
   3;21,14,22;,
   3;14,23,22;,
   3;22,23,24;,
   3;25,27,26;,
   3;27,28,26;,
   3;26,28,29;,
   3;28,30,29;,
   3;29,30,31;,
   3;30,32,31;,
   3;31,32,33;,
   3;32,34,33;,
   3;33,34,20;,
   3;34,35,20;,
   3;20,35,36;,
   3;37,39,38;,
   3;39,40,38;,
   3;38,40,41;,
   3;40,42,41;,
   3;41,42,38;,
   3;42,43,38;,
   3;38,43,37;,
   3;43,44,37;,
   3;37,44,45;,
   3;44,42,45;,
   3;45,42,40;,
   3;46,48,47;,
   3;48,49,47;,
   3;47,49,50;,
   3;49,51,50;,
   3;50,51,52;,
   3;51,48,52;,
   3;52,48,53;,
   3;48,46,53;,
   3;53,46,52;,
   3;46,54,52;,
   3;52,54,50;,
   3;55,57,56;,
   3;57,58,56;,
   3;56,58,59;,
   3;58,60,59;,
   3;59,60,61;,
   3;60,57,61;,
   3;61,57,62;,
   3;57,55,62;,
   3;62,55,61;,
   3;55,63,61;,
   3;61,63,59;,
   3;64,66,65;,
   3;66,67,65;,
   3;65,67,68;,
   3;67,69,68;,
   3;68,69,70;,
   3;69,66,70;,
   3;70,66,71;,
   3;66,64,71;,
   3;71,64,70;,
   3;64,72,70;,
   3;70,72,68;,
   3;73,75,74;,
   3;75,76,74;,
   3;74,76,77;,
   3;76,78,77;,
   3;77,78,74;,
   3;78,79,74;,
   3;74,79,73;,
   3;79,80,73;,
   3;73,80,81;,
   3;80,78,81;,
   3;81,78,76;,
   3;82,84,83;,
   3;84,85,83;,
   3;83,85,18;,
   3;85,86,18;,
   3;18,86,8;,
   3;86,87,8;,
   3;8,87,6;,
   3;87,1,6;,
   3;6,1,4;,
   3;88,90,89;,
   3;90,161,89;,
   3;89,91,92;,
   3;91,93,92;,
   3;92,93,94;,
   3;93,95,94;,
   3;94,95,96;,
   3;95,97,96;,
   3;98,100,99;,
   3;100,101,99;,
   3;99,101,102;,
   3;101,103,102;,
   3;102,103,104;,
   3;103,101,104;,
   3;104,101,105;,
   3;101,100,105;,
   3;105,100,106;,
   3;107,109,108;,
   3;107,108,110;,
   3;107,110,111;,
   3;107,111,112;,
   3;107,112,113;,
   3;107,113,114;,
   3;107,114,1;,
   3;107,1,109;,
   3;115,22,116;,
   3;115,116,117;,
   3;115,117,118;,
   3;115,118,119;,
   3;115,119,31;,
   3;115,31,33;,
   3;115,33,21;,
   3;115,21,22;,
   3;120,88,30;,
   3;30,88,32;,
   3;88,89,32;,
   3;32,89,34;,
   3;89,96,34;,
   3;34,96,35;,
   3;121,123,122;,
   3;123,124,122;,
   3;122,124,125;,
   3;124,126,125;,
   3;125,126,127;,
   3;126,128,127;,
   3;5,3,129;,
   3;5,129,130;,
   3;5,130,131;,
   3;5,131,9;,
   3;5,9,7;,
   3;18,132,83;,
   3;132,82,83;,
   3;102,104,133;,
   3;104,105,133;,
   3;133,105,98;,
   3;105,106,98;,
   3;30,28,134;,
   3;134,28,27;,
   3;111,110,135;,
   3;110,108,135;,
   3;136,138,137;,
   3;137,138,87;,
   3;87,140,139;,
   3;140,141,139;,
   3;139,141,142;,
   3;92,94,89;,
   3;94,96,89;,
   3;2,143,3;,
   3;143,129,3;,
   3;144,146,145;,
   3;146,147,145;,
   3;128,126,123;,
   3;126,124,123;,
   3;148,150,149;,
   3;150,151,149;,
   3;152,154,153;,
   3;154,155,153;,
   3;156,157,152;,
   3;157,154,152;,
   3;158,156,153;,
   3;156,152,153;,
   3;159,158,155;,
   3;158,153,155;,
   3;157,159,154;,
   3;159,155,154;,
   3;1,87,109;,
   3;0,1,114;,
   3;22,24,116;,
   3;20,21,33;,
   3;85,84,160;,
   3;25,26,29;,
   3;131,11,9;,
   3;20,36,19;,
   3;42,44,43;,
   3;48,51,49;,
   3;57,60,58;,
   3;66,69,67;,
   3;78,80,79;,
   3;106,100,98;,
   3;29,31,119;;

   MeshNormals {
    162;
    0.959269;0.148274;0.240454;,
    0.318009;-0.943800;0.090064;,
    0.941767;0.116143;0.315571;,
    0.414091;-0.883568;0.218714;,
    0.120781;-0.992597;0.012756;,
    0.004445;-0.977194;0.212303;,
    0.002628;-0.999996;0.000278;,
    -0.122537;-0.992413;0.010080;,
    -0.318253;-0.944303;0.083707;,
    -0.434434;-0.852602;0.290408;,
    -0.924605;0.109221;0.364934;,
    -0.629572;0.100919;0.770360;,
    -0.324460;0.915167;0.239156;,
    -0.157158;0.854534;0.495049;,
    0.013232;0.991185;0.131819;,
    0.181421;0.884619;0.429576;,
    -0.071627;-0.984795;0.158269;,
    -0.346617;-0.920888;0.178387;,
    -0.340787;-0.938921;0.047880;,
    -0.963907;0.099378;0.246998;,
    -0.281922;0.955933;-0.081924;,
    0.008916;0.998673;-0.050714;,
    0.326488;0.927375;0.182703;,
    0.181421;0.884619;0.429576;,
    0.727658;0.069591;0.682401;,
    0.951747;-0.226515;-0.207049;,
    0.736597;0.333581;-0.588343;,
    0.764262;-0.145232;-0.628340;,
    -0.200788;0.490846;-0.847794;,
    0.293703;0.841306;-0.453810;,
    -0.104348;0.835635;-0.539282;,
    0.096826;0.935341;-0.340239;,
    0.005574;0.915652;-0.401933;,
    0.002914;0.978268;-0.207322;,
    -0.087448;0.928738;-0.360275;,
    -0.190598;0.935930;-0.296154;,
    -0.398633;0.914432;-0.070041;,
    0.164282;-0.940271;-0.298164;,
    0.091533;-0.868768;0.486686;,
    0.238543;-0.700063;0.673059;,
    -0.626145;0.157665;0.763600;,
    -0.525196;-0.355787;0.773036;,
    -0.819370;-0.555597;0.141227;,
    0.038055;-0.980213;-0.194252;,
    -0.379331;-0.840437;-0.387006;,
    -0.961743;-0.140525;-0.235166;,
    0.574676;0.269757;0.772644;,
    0.960882;0.112028;-0.253291;,
    0.864147;-0.485326;0.133073;,
    0.402842;-0.791235;-0.460072;,
    -0.219770;-0.921494;-0.320233;,
    -0.029740;-0.974055;-0.224348;,
    -0.164972;-0.848035;0.503609;,
    0.529223;-0.334361;0.779824;,
    -0.401064;-0.561940;0.723444;,
    0.302011;0.621339;0.722998;,
    0.621765;0.671053;-0.403851;,
    0.951815;-0.070527;0.298453;,
    0.513555;-0.597258;-0.616072;,
    -0.171260;-0.891109;-0.420232;,
    0.071148;-0.984048;-0.163056;,
    -0.084800;-0.820765;0.564937;,
    0.420415;0.096271;0.902210;,
    -0.246609;-0.659443;0.710154;,
    -0.552242;-0.286767;0.782811;,
    -0.958547;0.068147;-0.276664;,
    -0.805475;0.545575;0.231427;,
    -0.455822;0.776321;-0.435376;,
    0.306448;0.856275;-0.415792;,
    0.065789;0.983927;-0.166008;,
    0.243461;0.790722;0.561681;,
    -0.519288;0.289670;0.804009;,
    0.528787;0.432837;0.730093;,
    -0.188027;0.918725;-0.347261;,
    -0.147391;0.807175;0.571616;,
    -0.393654;0.530169;0.750971;,
    0.560666;-0.194299;0.804923;,
    0.481074;0.361956;0.798471;,
    0.766901;0.590218;0.252005;,
    -0.012552;0.991505;-0.129462;,
    0.451746;0.807968;-0.378303;,
    0.957644;0.159411;-0.239804;,
    -0.821247;-0.345862;-0.453799;,
    -0.645195;-0.730608;-0.223463;,
    0.438805;-0.202042;-0.875574;,
    0.535150;-0.679029;-0.502528;,
    0.184870;-0.932537;-0.310157;,
    -0.005761;-0.984291;-0.176458;,
    0.008358;0.882800;-0.469675;,
    -0.011899;0.824687;-0.565464;,
    -0.403823;0.622504;-0.670385;,
    0.985995;0.024442;-0.164977;,
    0.652791;0.417444;-0.632143;,
    0.438805;-0.202042;-0.875574;,
    -0.281210;0.521235;-0.805751;,
    -0.821247;-0.345862;-0.453799;,
    -0.620148;0.634822;-0.460888;,
    -0.973185;-0.097264;0.208448;,
    -0.450256;0.459169;-0.765790;,
    -0.054989;0.674528;0.736198;,
    -0.812239;0.354072;0.463573;,
    -0.199944;-0.564039;0.801175;,
    0.355189;-0.576344;0.735981;,
    0.139094;-0.877077;0.459771;,
    0.124288;-0.979503;-0.158515;,
    -0.398501;-0.794223;-0.458702;,
    -0.842647;-0.176367;-0.508764;,
    0.569423;-0.821241;-0.036347;,
    -0.169990;-0.830650;-0.530211;,
    -0.207036;-0.924165;-0.321021;,
    0.407454;-0.781630;-0.472266;,
    0.764262;-0.145232;-0.628340;,
    0.951747;-0.226515;-0.207049;,
    0.528379;-0.834882;-0.154229;,
    0.372222;-0.911776;0.173537;,
    0.264086;0.959906;-0.094020;,
    0.941767;0.116143;0.315571;,
    0.959269;0.148274;0.240454;,
    0.344702;0.932479;-0.107994;,
    0.181817;0.949748;-0.254797;,
    0.097627;0.878478;-0.467703;,
    0.151927;-0.987541;-0.041000;,
    0.967809;-0.228477;0.105565;,
    0.537848;-0.554028;-0.635431;,
    0.538801;0.777583;-0.324127;,
    -0.169563;0.984539;0.043953;,
    -0.296326;0.819347;-0.490776;,
    -0.974444;0.198903;-0.104385;,
    -0.534600;-0.536138;-0.653267;,
    0.310280;-0.845210;0.435139;,
    0.000598;-0.815820;0.578306;,
    -0.297061;-0.798361;0.523807;,
    -0.973185;-0.097264;0.208448;,
    0.099331;-0.737975;-0.667477;,
    -0.248576;-0.537462;-0.805819;,
    -0.248576;-0.537462;-0.805819;,
    -0.169990;-0.830650;-0.530211;,
    -0.623774;-0.183150;-0.759843;,
    -0.207036;-0.924165;-0.321021;,
    0.623208;-0.410544;-0.665632;,
    0.184870;-0.932537;-0.310157;,
    0.535150;-0.679029;-0.502528;,
    0.751536;-0.213936;-0.624039;,
    0.727658;0.069591;0.682401;,
    0.537848;-0.554028;-0.635431;,
    -0.534600;-0.536138;-0.653267;,
    0.151927;-0.987541;-0.041000;,
    -0.974444;0.198903;-0.104385;,
    0.950523;0.125830;-0.284031;,
    0.940530;0.165896;-0.296448;,
    0.940530;0.165896;-0.296448;,
    0.928789;0.205653;-0.308314;,
    0.148394;-0.917666;0.368603;,
    0.558554;-0.829327;0.015289;,
    -0.304129;-0.951812;-0.039484;,
    0.063678;-0.419106;-0.905702;,
    0.463576;-0.761821;0.452467;,
    -0.348250;-0.915973;0.199289;,
    0.733589;0.039683;-0.678434;,
    -0.473460;-0.437790;-0.764313;,
    0.895231;0.385086;-0.224211;,
    -0.650427;0.282821;-0.704952;;
    200;
    3;0,2,1;,
    3;2,3,1;,
    3;1,3,4;,
    3;3,5,4;,
    3;4,5,6;,
    3;5,7,6;,
    3;6,7,8;,
    3;7,9,8;,
    3;8,9,10;,
    3;9,11,10;,
    3;10,11,12;,
    3;11,13,12;,
    3;12,13,14;,
    3;13,15,14;,
    3;16,18,17;,
    3;18,8,17;,
    3;17,8,19;,
    3;8,10,19;,
    3;19,10,20;,
    3;10,12,20;,
    3;20,12,21;,
    3;12,14,21;,
    3;21,14,22;,
    3;14,23,22;,
    3;22,23,24;,
    3;25,27,26;,
    3;27,28,26;,
    3;26,28,29;,
    3;28,30,29;,
    3;29,30,31;,
    3;30,32,31;,
    3;31,32,33;,
    3;32,34,33;,
    3;33,34,20;,
    3;34,35,20;,
    3;20,35,36;,
    3;37,39,38;,
    3;39,40,38;,
    3;38,40,41;,
    3;40,42,41;,
    3;41,42,38;,
    3;42,43,38;,
    3;38,43,37;,
    3;43,44,37;,
    3;37,44,45;,
    3;44,42,45;,
    3;45,42,40;,
    3;46,48,47;,
    3;48,49,47;,
    3;47,49,50;,
    3;49,51,50;,
    3;50,51,52;,
    3;51,48,52;,
    3;52,48,53;,
    3;48,46,53;,
    3;53,46,52;,
    3;46,54,52;,
    3;52,54,50;,
    3;55,57,56;,
    3;57,58,56;,
    3;56,58,59;,
    3;58,60,59;,
    3;59,60,61;,
    3;60,57,61;,
    3;61,57,62;,
    3;57,55,62;,
    3;62,55,61;,
    3;55,63,61;,
    3;61,63,59;,
    3;64,66,65;,
    3;66,67,65;,
    3;65,67,68;,
    3;67,69,68;,
    3;68,69,70;,
    3;69,66,70;,
    3;70,66,71;,
    3;66,64,71;,
    3;71,64,70;,
    3;64,72,70;,
    3;70,72,68;,
    3;73,75,74;,
    3;75,76,74;,
    3;74,76,77;,
    3;76,78,77;,
    3;77,78,74;,
    3;78,79,74;,
    3;74,79,73;,
    3;79,80,73;,
    3;73,80,81;,
    3;80,78,81;,
    3;81,78,76;,
    3;82,84,83;,
    3;84,85,83;,
    3;83,85,18;,
    3;85,86,18;,
    3;18,86,8;,
    3;86,87,8;,
    3;8,87,6;,
    3;87,1,6;,
    3;6,1,4;,
    3;88,90,89;,
    3;90,161,89;,
    3;89,91,92;,
    3;91,93,92;,
    3;92,93,94;,
    3;93,95,94;,
    3;94,95,96;,
    3;95,97,96;,
    3;98,100,99;,
    3;100,101,99;,
    3;99,101,102;,
    3;101,103,102;,
    3;102,103,104;,
    3;103,101,104;,
    3;104,101,105;,
    3;101,100,105;,
    3;105,100,106;,
    3;107,109,108;,
    3;107,108,110;,
    3;107,110,111;,
    3;107,111,112;,
    3;107,112,113;,
    3;107,113,114;,
    3;107,114,1;,
    3;107,1,109;,
    3;115,22,116;,
    3;115,116,117;,
    3;115,117,118;,
    3;115,118,119;,
    3;115,119,31;,
    3;115,31,33;,
    3;115,33,21;,
    3;115,21,22;,
    3;120,88,30;,
    3;30,88,32;,
    3;88,89,32;,
    3;32,89,34;,
    3;89,96,34;,
    3;34,96,35;,
    3;121,123,122;,
    3;123,124,122;,
    3;122,124,125;,
    3;124,126,125;,
    3;125,126,127;,
    3;126,128,127;,
    3;5,3,129;,
    3;5,129,130;,
    3;5,130,131;,
    3;5,131,9;,
    3;5,9,7;,
    3;18,132,83;,
    3;132,82,83;,
    3;102,104,133;,
    3;104,105,133;,
    3;133,105,98;,
    3;105,106,98;,
    3;30,28,134;,
    3;134,28,27;,
    3;111,110,135;,
    3;110,108,135;,
    3;136,138,137;,
    3;137,138,87;,
    3;87,140,139;,
    3;140,141,139;,
    3;139,141,142;,
    3;92,94,89;,
    3;94,96,89;,
    3;2,143,3;,
    3;143,129,3;,
    3;144,146,145;,
    3;146,147,145;,
    3;128,126,123;,
    3;126,124,123;,
    3;148,150,149;,
    3;150,151,149;,
    3;152,154,153;,
    3;154,155,153;,
    3;156,157,152;,
    3;157,154,152;,
    3;158,156,153;,
    3;156,152,153;,
    3;159,158,155;,
    3;158,153,155;,
    3;157,159,154;,
    3;159,155,154;,
    3;1,87,109;,
    3;0,1,114;,
    3;22,24,116;,
    3;20,21,33;,
    3;85,84,160;,
    3;25,26,29;,
    3;131,11,9;,
    3;20,36,19;,
    3;42,44,43;,
    3;48,51,49;,
    3;57,60,58;,
    3;66,69,67;,
    3;78,80,79;,
    3;106,100,98;,
    3;29,31,119;;
   }

   MeshTextureCoords {
    162;
    -0.001302;0.581166;,
    0.134800;0.382408;,
    0.029300;0.890731;,
    0.160200;0.906622;,
    0.166000;0.596943;,
    0.259800;0.934534;,
    0.257800;0.600814;,
    0.367200;0.600814;,
    0.374390;0.426275;,
    0.363300;0.906622;,
    0.494100;0.890731;,
    0.490200;0.942480;,
    0.576200;0.902751;,
    0.627000;0.962242;,
    0.695300;0.954297;,
    0.736300;0.994025;,
    0.423177;0.207578;,
    0.419875;0.415727;,
    0.402300;0.179894;,
    0.520198;0.581166;,
    0.632115;0.457817;,
    0.710900;0.775620;,
    0.576200;0.902751;,
    0.627000;0.962242;,
    0.490200;0.942480;,
    0.646500;0.064783;,
    0.654300;0.171949;,
    0.660200;0.060709;,
    0.709000;0.167874;,
    0.643908;0.211677;,
    0.712900;0.219623;,
    0.691400;0.295006;,
    0.781300;0.362646;,
    0.753900;0.521357;,
    0.691400;0.295006;,
    0.647142;0.260609;,
    0.619829;0.422200;,
    0.994100;0.295006;,
    0.978500;0.449845;,
    0.996100;0.465737;,
    0.865200;0.465737;,
    0.882800;0.453920;,
    0.880900;0.410320;,
    0.978500;0.318843;,
    0.877000;0.314972;,
    0.857400;0.287060;,
    0.865200;0.465737;,
    0.857400;0.287060;,
    0.880900;0.410320;,
    0.877000;0.314972;,
    0.994100;0.295006;,
    0.978500;0.318843;,
    0.978500;0.449845;,
    0.882800;0.453920;,
    0.996100;0.465737;,
    0.839800;0.636672;,
    0.732400;0.640543;,
    0.806600;0.708183;,
    0.757800;0.704108;,
    0.734400;0.998100;,
    0.755900;0.962242;,
    0.832000;0.958371;,
    0.830100;0.727945;,
    0.841800;0.998100;,
    0.992200;0.453920;,
    0.841800;0.441899;,
    0.953100;0.485703;,
    0.871100;0.489574;,
    0.843800;0.656434;,
    0.869100;0.612834;,
    0.986300;0.612834;,
    0.982400;0.497520;,
    0.994100;0.668454;,
    0.052700;0.156057;,
    0.044900;0.362646;,
    0.054700;0.378537;,
    0.003900;0.394429;,
    0.009800;0.378537;,
    0.009800;0.342680;,
    0.043000;0.215548;,
    0.013700;0.219623;,
    0.000000;0.164003;,
    0.431600;0.052763;,
    0.406300;0.151982;,
    0.370471;0.019788;,
    0.347700;0.132220;,
    0.306600;0.227569;,
    0.257800;0.267297;,
    0.787779;0.273848;,
    0.712900;0.219623;,
    0.736992;0.206411;,
    0.710271;0.071333;,
    0.709000;0.167874;,
    0.696571;0.067463;,
    0.654300;0.171949;,
    0.660200;0.060709;,
    0.643908;0.211677;,
    0.646500;0.064783;,
    0.996100;0.668454;,
    0.996100;0.998100;,
    0.984400;0.982209;,
    0.916000;0.982209;,
    0.832000;0.994025;,
    0.857400;0.982209;,
    0.853500;0.684346;,
    0.916000;0.680271;,
    0.984400;0.676400;,
    0.105500;0.175820;,
    0.160200;0.120403;,
    0.210900;0.223494;,
    0.099600;0.140166;,
    0.076200;0.040946;,
    0.064500;0.056838;,
    0.080100;0.215548;,
    0.089208;0.401042;,
    0.632115;0.457817;,
    0.494100;0.890731;,
    0.520198;0.581166;,
    0.619829;0.422200;,
    0.647142;0.260609;,
    0.736992;0.206411;,
    0.793000;0.457791;,
    0.802700;0.461866;,
    0.793000;0.279114;,
    0.802700;0.314972;,
    0.851600;0.461866;,
    0.853500;0.314972;,
    0.863300;0.449845;,
    0.867200;0.291135;,
    0.177700;0.974263;,
    0.259800;0.990154;,
    0.332000;0.982209;,
    0.439500;0.068654;,
    0.832000;0.652563;,
    0.699175;0.059502;,
    0.105475;0.007753;,
    0.347700;0.132220;,
    0.281935;0.230362;,
    0.306600;0.227569;,
    0.195300;0.140166;,
    0.210900;0.223494;,
    0.160200;0.120403;,
    0.122371;0.003897;,
    0.035200;0.942480;,
    0.802700;0.314972;,
    0.853500;0.314972;,
    0.802700;0.461866;,
    0.851600;0.461866;,
    0.355500;-0.205371;,
    0.367200;-0.217188;,
    0.359400;-0.229208;,
    0.371100;-0.237154;,
    0.783200;0.525431;,
    0.785200;0.644617;,
    0.828100;0.521357;,
    0.828100;0.636672;,
    0.771500;0.497520;,
    0.839800;0.497520;,
    0.771500;0.664380;,
    0.839800;0.660509;,
    0.358690;0.026336;,
    0.710271;0.071333;;
   }

   VertexDuplicationIndices {
    162;
    161;
    0,
    1,
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    10,
    11,
    12,
    13,
    14,
    15,
    16,
    17,
    18,
    19,
    20,
    21,
    22,
    23,
    24,
    25,
    26,
    27,
    28,
    29,
    30,
    31,
    32,
    33,
    34,
    35,
    36,
    37,
    38,
    39,
    40,
    41,
    42,
    43,
    44,
    45,
    46,
    47,
    48,
    49,
    50,
    51,
    52,
    53,
    54,
    55,
    56,
    57,
    58,
    59,
    60,
    61,
    62,
    63,
    64,
    65,
    66,
    67,
    68,
    69,
    70,
    71,
    72,
    73,
    74,
    75,
    76,
    77,
    78,
    79,
    80,
    81,
    82,
    83,
    84,
    85,
    86,
    87,
    88,
    89,
    90,
    91,
    92,
    93,
    94,
    95,
    96,
    97,
    98,
    99,
    100,
    101,
    102,
    103,
    104,
    105,
    106,
    107,
    108,
    109,
    110,
    111,
    112,
    113,
    114,
    115,
    116,
    117,
    118,
    119,
    120,
    121,
    122,
    123,
    124,
    125,
    126,
    127,
    128,
    129,
    130,
    131,
    132,
    133,
    134,
    135,
    136,
    137,
    138,
    139,
    140,
    141,
    142,
    143,
    144,
    145,
    146,
    147,
    148,
    149,
    150,
    151,
    152,
    153,
    154,
    155,
    156,
    157,
    158,
    159,
    160,
    91;
   }

   MeshMaterialList {
    1;
    200;
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0;

    Material body_bmp {
     0.800000;0.800000;0.800000;1.000000;;
     0.000000;
     0.000000;0.000000;0.000000;;
     0.000000;0.000000;0.000000;;

     TextureFilename {
      "vest.png";
     }
    }
   }

   XSkinMeshHeader {
    1;
    1;
    1;
   }

   SkinWeights {
    "body_bmp";
    162;
    0,
    1,
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    10,
    11,
    12,
    13,
    14,
    15,
    16,
    17,
    18,
    19,
    20,
    21,
    22,
    23,
    24,
    25,
    26,
    27,
    28,
    29,
    30,
    31,
    32,
    33,
    34,
    35,
    36,
    37,
    38,
    39,
    40,
    41,
    42,
    43,
    44,
    45,
    46,
    47,
    48,
    49,
    50,
    51,
    52,
    53,
    54,
    55,
    56,
    57,
    58,
    59,
    60,
    61,
    62,
    63,
    64,
    65,
    66,
    67,
    68,
    69,
    70,
    71,
    72,
    73,
    74,
    75,
    76,
    77,
    78,
    79,
    80,
    81,
    82,
    83,
    84,
    85,
    86,
    87,
    88,
    89,
    90,
    91,
    92,
    93,
    94,
    95,
    96,
    97,
    98,
    99,
    100,
    101,
    102,
    103,
    104,
    105,
    106,
    107,
    108,
    109,
    110,
    111,
    112,
    113,
    114,
    115,
    116,
    117,
    118,
    119,
    120,
    121,
    122,
    123,
    124,
    125,
    126,
    127,
    128,
    129,
    130,
    131,
    132,
    133,
    134,
    135,
    136,
    137,
    138,
    139,
    140,
    141,
    142,
    143,
    144,
    145,
    146,
    147,
    148,
    149,
    150,
    151,
    152,
    153,
    154,
    155,
    156,
    157,
    158,
    159,
    160,
    161;
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000,
    1.000000;
    1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,0.000000,0.000000,0.000000,1.000000;;
   }
  }
 }
}