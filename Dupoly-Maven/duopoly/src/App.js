import "./App.css";
import "./style.css";
import {Component } from "react";
import PlayerTable from "./components/playertable";
import TransTable from "./components/transtable";
import UpdateDirection from "./components/direction";


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
            <div className="container_right">
                <PlayerTable />
                <TransTable />
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