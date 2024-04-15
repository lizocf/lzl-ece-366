import "./App.css";
import "./style.css";
import {Component } from "react";
import PlayerTable from "./components/playertable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
import Tiles from "./components/tiles";
import LeftTables from "./components/lefttables";

// function App() {
//     const [data, setData] = useState([]);
//     const loadUsers = async () => {
//           const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/1`);
//           setData(response.data)
//         }
//         useEffect(() => {
//             loadUsers();
//     }, []);
//     if (data)
//         return (
//             <pre>{JSON.stringify(data, null, 2)}</pre>
//         );
//     return <h1>Data</h1>;
// }

class App extends Component {
    render() {
        return (
            <>
            {/* <Move /> */}
            <meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>

            <div className="container_right"style={{margin: "-20vh auto"}}>
                <PlayerTable />
            </div>
            {/* <LeftTables/> */}
            <div className="container_middle">
                <div className="center" id="direction_div">
                    <h1>Choose a direction!</h1>
                </div>
                <div className="center" id="roll_div" style={{display: "none"}}>
                    {/* <h1>Click to roll:</h1> */}
                </div>
                <Roll />
            </div>
            <UpdateDirection />
            
            </>
        )
    }
}
  

export default App;



// function Home() {
//   const [data, setData] = useState(null);
//   useEffect(() => {

//       const loadUsers = async () => {
//           const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/1`);
//           setData(response.data)
//       }
//       loadUsers();
//   }, []);
//   if (data)
//       return (
//           <>
//               <h1>RPS</h1>
//               <nav>
//                   <Link to="/about">About</Link>
//                   <Link to="/contact">Contact</Link>
//               </nav>
//               <pre>{JSON.stringify(data, null, 2)}</pre>
//           </>
//       );
//   return <h1>Data</h1>;
// }