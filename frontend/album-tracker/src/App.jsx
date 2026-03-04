import { Routes, Route } from 'react-router-dom';
import Album from './views/Album';
import TopBar from './components/TopBar';
import './App.css';

function App() {
  return (
    <>
      <TopBar></TopBar>
      <Routes>
        <Route index element={ <></> }></Route>
        <Route path="album">
          <Route path=":albumid" element={ <Album /> }></Route>
        </Route>
      </Routes>
    </>
  )
}

export default App;
