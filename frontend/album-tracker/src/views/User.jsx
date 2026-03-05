import './User.css';
import { useContext, useEffect, useState } from "react";
import { LoginContext } from "../LoginContext";
import { Link, useParams } from 'react-router-dom';

function User() {
    const [ user, setUser ] = useState();
    const [ pfpUrl, setPfpUrl ] = useState(null);
    const [ username, setUsername ] = useState("");
    const [ albumsList, setAlbumsList ] = useState([]);
    const loginContext = useContext(LoginContext);
    const params = useParams();

    useEffect(() => {
        let userReq = new XMLHttpRequest();
        userReq.open("GET", `http://localhost:8080/users/${params.userid}`);

        userReq.onload = () => {
            let data = JSON.parse(userReq.responseText);

            setPfpUrl(data.pfpUrl);
            setUsername(data.username);
            setAlbumsList(data.listenedAlbums);
            setUser(data);
        };

        userReq.send();
    }, []);

    function maybeRenderEditButton() {
        if (loginContext.user && user && loginContext.user.id == user.id) {
            return (<button className="createButton editButton">
                Edit...
            </button>)
        } else { return (<></>) }
    }

    const albumsListElements = albumsList.map(album => 
        <Link to={ `/album/${album.id}` } className="noPadding">
            <img className="inlineJacket" src={ album.jacketURL }></img>
        </Link>
    );

    return (
        <>
            <div className="userPageContainer">
                <div className="detailsContainer">
                    <img className="avatar" src={ pfpUrl }></img>
                    <div className="detailsRight">
                        <span className="username">{ username }</span>
                        { maybeRenderEditButton() }
                    </div>
                </div>
                <div className="albumsContainer">
                    { albumsListElements }
                </div>
            </div>
        </>
    )
}

export default User;