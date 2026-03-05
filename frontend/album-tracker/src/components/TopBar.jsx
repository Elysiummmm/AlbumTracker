import { Link } from 'react-router-dom';
import './TopBar.css';
import { LoginContext } from '../LoginContext';
import { useContext } from 'react';

function TopBar() {
    const userContext = useContext(LoginContext);

    let userPageLink;

    if (!userContext.user) {
        userPageLink = <Link className="topBarLink" to="/login">Log in</Link>
    } else {
        userPageLink = <Link className="topBarLink" to={ `/user/${userContext.user.id}` }></Link>
    }

    return (
        <nav>
            <Link className="topBarLink" to="/">Home</Link>
            <Link className="topBarLink" to="/albums">Albums</Link>
            <Link className="topBarLink" to="/artists">Artists</Link>

            {
                userContext.user
                ?   <Link className="topBarLink" to={ `/user/${userContext.user.id}` }>
                        { userContext.user.username }
                        <img className="profilePicture" src={ userContext.user.pfpUrl }></img>
                    </Link>
                : <Link className="topBarLink" to="/login">Log in</Link> 
            }
        </nav>
    )
}

export default TopBar;
