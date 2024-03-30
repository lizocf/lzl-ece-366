import "./App.css";
import "./style.css";
import {Component } from "react";
import PlayerTable from "./components/playertable";
import TransTable from "./components/transtable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
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
            <div className="container_right">
                <PlayerTable />
                <TransTable />
            </div>
            <div className="container_middle">
                <div className="center" id="direction_div">
                    <h1>Choose a direction!</h1>
                </div>
                <div className="center" id="roll_div" style={{display: "none"}}>
                    <h1>Click to roll:</h1>
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