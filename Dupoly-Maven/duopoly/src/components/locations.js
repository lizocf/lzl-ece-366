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

export default space