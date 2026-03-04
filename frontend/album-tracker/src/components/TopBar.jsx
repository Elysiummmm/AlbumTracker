import { Link } from 'react-router-dom';
import './TopBar.css';

function TopBar() {
    return (
        <header>
            <Link to="/home">Home</Link>
            <Link to="/albums">Albums</Link>
            <Link to="/artists">Artists</Link>
        </header>
    )
}

export default TopBar;
