import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";

let space = {};

space[0] = "top: 9%; left: 47.5%;";

space[1] = "top: 11%; left: 52.5%;";
space[2] = "top: 12.5%; left: 55.6%;";
space[3] = "top: 14%; left: 58.7%;";
space[4] = "top: 15.5%; left: 61.8%;";
space[5] = "top: 17%; left: 64.9%;";
space[6] = "top: 19.5%; left: 68%;";
space[7] = "top: 21%; left: 71.1%;";
space[8] = "top: 22.5%; left: 74.2%;";
space[9] = "top: 24%; left: 77.3%;";

space[10] = "top: 27%; left: 84%;";

space[11] = "top: 33%; left: 84%;";
space[12] = "top: 36.3%; left: 84%;";
space[13] = "top: 39.4%; left: 84%;";
space[14] = "top: 42.9%; left: 84%;";
space[15] = "top: 46.2%; left: 84%;";
space[16] = "top: 49.5%; left: 84%;";
space[17] = "top: 52.8%; left: 84%;";
space[18] = "top: 56.1%; left: 84%;";
space[19] = "top: 59.4%; left: 84%;";

space[20] = "top: 67%; left: 84%;";

space[21] = "top: 70.5%; left: 77.7%;";
space[22] = "top: 72%; left: 74.675%;";
space[23] = "top: 73.5%; left: 71.65%;";
space[24] = "top: 75%; left: 68.625%;";
space[25] = "top: 76.5%; left: 65.6%;";
space[26] = "top: 78%; left: 62.575%;";
space[27] = "top: 79.5%; left: 59.55%;";
space[28] = "top: 81%; left: 56.525%;";
space[29] = "top: 83.5%; left: 53.5%;";

space[30] = "top: 86%; left: 47.5%;";

space[31] = "top: 83.5%; left: 40.5%;";
space[32] = "top: 82%; left: 37.4%;";
space[33] = "top: 80.5%; left: 34.3%;";
space[34] = "top: 79%; left: 31.2%;";
space[35] = "top: 77.5%; left: 28.1%;";
space[36] = "top: 76%; left: 25%;";
space[37] = "top: 74.5%; left: 21.9%;";
space[38] = "top: 73%; left: 18.8%;";
space[39] = "top: 70.5%; left: 15.7%;";

space[40] = "top: 67%; left: 10%;";

space[41] = "top: 59.4%; left: 10%;";
space[42] = "top: 56.1%; left: 10%;";
space[43] = "top: 52.8%; left: 10%;";
space[44] = "top: 49.5%; left: 10%;";
space[45] = "top: 46.2%; left: 10%;";
space[46] = "top: 42.9%; left: 10%;";
space[47] = "top: 39.4%; left: 10%;";
space[48] = "top: 36.3%; left: 10%;";
space[49] = "top: 33%; left: 10%;";

// space[50] = "top: 27%; left: 10%;";
space[50] = "top: 23.5%; left: 5%;";

space[51] = "top: 24%; left: 16.5%;";
space[52] = "top: 22.5%; left: 19.5875%;";
space[53] = "top: 21%; left: 22.675%;";
space[54] = "top: 19.5%; left: 25.7625%;";
space[55] = "top: 17%; left: 28.85%;";
space[56] = "top: 15.5%; left: 31.9375%;";
space[57] = "top: 14%; left: 35.025%;";
space[58] = "top: 12.5%; left: 38.1125%;";
space[59] = "top: 11%; left: 41.2%;";

let costPrice = {};

costPrice[0] = null;

costPrice[1] = 20;
costPrice[2] = 20;
costPrice[3] = 100;
costPrice[4] = 100;
costPrice[5] = 60;
costPrice[6] = 80;
costPrice[7] = 100;
costPrice[8] = null;
costPrice[9] = 100;

costPrice[10] = null;

costPrice[11] = 60;
costPrice[12] = 100;
costPrice[13] = 80;
costPrice[14] = 100;
costPrice[15] = 100;
costPrice[16] = null;
costPrice[17] = 60;
costPrice[18] = 80;
costPrice[19] = 100;

costPrice[20] = null;

costPrice[21] = 150;
costPrice[22] = null;
costPrice[23] = 150;
costPrice[24] = 160;
costPrice[25] = 100;
costPrice[26] = 100;
costPrice[27] = 180;
costPrice[28] = null;
costPrice[29] = 200;

costPrice[30] = null;

costPrice[31] = 200;
costPrice[32] = null;
costPrice[33] = 180;
costPrice[34] = 100;
costPrice[35] = 100;
costPrice[36] = 200;
costPrice[37] = 160;
costPrice[38] = 150;
costPrice[39] = 100;

costPrice[40] = null;

costPrice[41] = 100;
costPrice[42] = 80;
costPrice[43] = 60;
costPrice[44] = null;
costPrice[45] = 100;
costPrice[46] = 100;
costPrice[47] = 80;
costPrice[48] = 100;
costPrice[49] = 60;

costPrice[50] = 100;

costPrice[51] = 100;
costPrice[52] = null;
costPrice[53] = 80;
costPrice[54] = 60;
costPrice[55] = 100;
costPrice[56] = 100;
costPrice[57] = 20;
costPrice[58] = null;
costPrice[59] = 20;

let buildingPrice = {};

buildingPrice[0] = null;

buildingPrice[1] = 10;
buildingPrice[2] = 10;
buildingPrice[3] = 100;
buildingPrice[4] = 100;
buildingPrice[5] = 30;
buildingPrice[6] = 35;
buildingPrice[7] = 40;
buildingPrice[8] = null;
buildingPrice[9] = 40;

buildingPrice[10] = null;

buildingPrice[11] = 30;
buildingPrice[12] = 100;
buildingPrice[13] = 35;
buildingPrice[14] = 40;
buildingPrice[15] = 100;
buildingPrice[16] = null;
buildingPrice[17] = 30;
buildingPrice[18] = 35;
buildingPrice[19] = 40;

buildingPrice[20] = null;

buildingPrice[21] = 50;
buildingPrice[22] = null;
buildingPrice[23] = 50;
buildingPrice[24] = 60;
buildingPrice[25] = 100;
buildingPrice[26] = 100;
buildingPrice[27] = 80;
buildingPrice[28] = null;
buildingPrice[29] = 100;

buildingPrice[30] = null;

buildingPrice[31] = 100;
buildingPrice[32] = null;
buildingPrice[33] = 80;
buildingPrice[34] = 100;
buildingPrice[35] = 100;
buildingPrice[36] = 100;
buildingPrice[37] = 60;
buildingPrice[38] = 50;
buildingPrice[39] = 100;

buildingPrice[40] = null;

buildingPrice[41] = 40;
buildingPrice[42] = 35;
buildingPrice[43] = 30;
buildingPrice[44] = null;
buildingPrice[45] = 100;
buildingPrice[46] = 40;
buildingPrice[47] = 35;
buildingPrice[48] = 100;
buildingPrice[49] = 30;

buildingPrice[50] = 100;

buildingPrice[51] = 40;
buildingPrice[52] = null;
buildingPrice[53] = 35;
buildingPrice[54] = 30;
buildingPrice[55] = 100;
buildingPrice[56] = 100;
buildingPrice[57] = 10;
buildingPrice[58] = null;
buildingPrice[59] = 10;

let rentPrice = {};

rentPrice[0] = null;

rentPrice[1] = 20;
rentPrice[2] = 20;
rentPrice[3] = 100;
rentPrice[4] = 100;
rentPrice[5] = 160;
rentPrice[6] = 180;
rentPrice[7] = 200;
rentPrice[8] = null;
rentPrice[9] = 200;

rentPrice[10] = null;

rentPrice[11] = 160;
rentPrice[12] = 100;
rentPrice[13] = 180;
rentPrice[14] = 200;
rentPrice[15] = 100;
rentPrice[16] = null;
rentPrice[17] = 160;
rentPrice[18] = 180;
rentPrice[19] = 200;

rentPrice[20] = null;

rentPrice[21] = 220;
rentPrice[22] = null;
rentPrice[23] = 220;
rentPrice[24] = 250;
rentPrice[25] = 100;
rentPrice[26] = 100;
rentPrice[27] = 350;
rentPrice[28] = null;
rentPrice[29] = 400;

rentPrice[30] = null;

rentPrice[31] = 400;
rentPrice[32] = null;
rentPrice[33] = 350;
rentPrice[34] = 100;
rentPrice[35] = 100;
rentPrice[36] = 400;
rentPrice[37] = 180;
rentPrice[38] = 220;
rentPrice[39] = 100;

rentPrice[40] = null;

rentPrice[41] = 200;
rentPrice[42] = 180;
rentPrice[43] = 160;
rentPrice[44] = null;
rentPrice[45] = 100;
rentPrice[46] = 200;
rentPrice[47] = 180;
rentPrice[48] = 100;
rentPrice[49] = 160;

rentPrice[50] = 100;

rentPrice[51] = 200;
rentPrice[52] = null;
rentPrice[53] = 180;
rentPrice[54] = 160;
rentPrice[55] = 100;
rentPrice[56] = 100;
rentPrice[57] = 20;
rentPrice[58] = null;
rentPrice[59] = 20;

let hotelPrice = {};

hotelPrice[0] = null;

hotelPrice[1] = 60;
hotelPrice[2] = 50;
hotelPrice[3] = 100;
hotelPrice[4] = 100;
hotelPrice[5] = 300;
hotelPrice[6] = 350;
hotelPrice[7] = 400;
hotelPrice[8] = null;
hotelPrice[9] = 400;

hotelPrice[10] = null;

hotelPrice[11] = 300;
hotelPrice[12] = 400;
hotelPrice[13] = 350;
hotelPrice[14] = 400;
hotelPrice[15] = 100;
hotelPrice[16] = null;
hotelPrice[17] = 300;
hotelPrice[18] = 350;
hotelPrice[19] = 400;

hotelPrice[20] = null;

hotelPrice[21] = 440;
hotelPrice[22] = null;
hotelPrice[23] = 440;
hotelPrice[24] = 500;
hotelPrice[25] = 100;
hotelPrice[26] = 100;
hotelPrice[27] = 450;
hotelPrice[28] = null;
hotelPrice[29] = 500;

hotelPrice[30] = null;

hotelPrice[31] = 500;
hotelPrice[32] = null;
hotelPrice[33] = 450;
hotelPrice[34] = 100;
hotelPrice[35] = 100;
hotelPrice[36] = 500;
hotelPrice[37] = 350;
hotelPrice[38] = 440;
hotelPrice[39] = 100;

hotelPrice[40] = null;

hotelPrice[41] = 400;
hotelPrice[42] = 350;
hotelPrice[43] = 300;
hotelPrice[44] = null;
hotelPrice[45] = 100;
hotelPrice[46] = 400;
hotelPrice[47] = 350;
hotelPrice[48] = 400;
hotelPrice[49] = 300;

hotelPrice[50] = 100;

hotelPrice[51] = 400;
hotelPrice[52] = null;
hotelPrice[53] = 350;
hotelPrice[54] = 300;
hotelPrice[55] = 100;
hotelPrice[56] = 100;
hotelPrice[57] = 60;
hotelPrice[58] = null;
hotelPrice[59] = 50;

export default { space, costPrice, buildingPrice, rentPrice, hotelPrice };