import { Routes, Route } from 'react-router-dom';
import Album from './views/Album';
import TopBar from './components/TopBar';
import './App.css';
import Login from './views/Login';
import Register from './views/Register';
import User from './views/User';
import { LoginContext } from './LoginContext.js'
import { useState, useMemo } from 'react';

function App() {
  // thanks bestie
  // https://stackoverflow.com/questions/74553077/is-there-a-way-of-changing-the-usecontext-value-in-a-child-so-the-other-childs-g
  const [ user, setUser ] = useState();
  const userContext = useMemo(() => ({ user, setUser }), [ user ]);

  return (
    <>
      <LoginContext.Provider value={ userContext }>
        <TopBar></TopBar>
        <Routes>
          <Route index element={ <></> }></Route>
          <Route path="album">
            <Route path=":albumid" element={ <Album /> }></Route>
          </Route>
          <Route path="user">
            <Route path=":userid" element={ <User /> }></Route>
          </Route>
          <Route path="albums" element={ <></> }></Route>
          <Route path="artists" element={ <></> }></Route>
          <Route path="login" element={ <Login /> }></Route>
          <Route path="register" element={ <Register /> }></Route>
        </Routes>
      </LoginContext.Provider>
    </>
  )
}

export default App;
