import './User.css';
import { useContext, useEffect, useState } from "react";
import { LoginContext } from "../LoginContext";
import { Link, useNavigate, useParams } from 'react-router-dom';

function User() {
    const [ user, setUser ] = useState();
    const [ pfpUrl, setPfpUrl ] = useState(null);
    const [ username, setUsername ] = useState("");
    const [ albumsList, setAlbumsList ] = useState([]);
    const loginContext = useContext(LoginContext);
    const params = useParams();
    const navigate = useNavigate();

    function reloadUserData() {
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
    }

    useEffect(() => { reloadUserData() }, []);

    function maybeRenderEditButton() {
        if (loginContext.user && user && loginContext.user.id == user.id) {
            return (<button className="createButton editButton"
                command="show-modal" commandfor="user-data-edit">
                Edit...
            </button>)
        } else { return (<></>) }
    }

    function handleUserDataEdit(event) {
        const form = event.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        let editUserReq = new XMLHttpRequest();
        editUserReq.open("PUT", `http://127.0.0.1:8080/users/${user.id}?username=${formJson.username}&pfpURL=${formJson.pfpUrl}`);

        editUserReq.onload = () => {
            let fetchUser = new XMLHttpRequest();
            fetchUser.open("GET", `http://127.0.0.1:8080/users/${editUserReq.responseText}`);

            fetchUser.onload = () => {
                loginContext.setUser(JSON.parse(fetchUser.responseText));
                reloadUserData();
            };

            fetchUser.send();
        };

        editUserReq.send();
    }

    const albumsListElements = albumsList.map(album => 
        <Link to={ `/album/${album.id}` } className="noPadding">
            <img className="inlineJacket" src={ album.jacketURL } title={ album.name }></img>
        </Link>
    );

    return (
        <>
            <dialog id="user-data-edit" closedby="any">
                <form method="dialog" onSubmit={ handleUserDataEdit }>
                    <input required name="username" type="text" placeholder="Username"></input>
                    <input required name="pfpUrl" type="url" placeholder="Avatar URL"></input>
                    <button type="submit">Confirm</button>
                </form>
            </dialog>

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