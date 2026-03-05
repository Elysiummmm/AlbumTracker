import { useNavigate, useParams } from 'react-router-dom';
import { useContext, useEffect, useState } from 'react';
import './album.css';
import { LoginContext } from '../LoginContext';

function Album() {
    const [ albumName, setAlbumName ] = useState("");
    const [ jacketURL, setJacketURL ] = useState("https://placehold.co/128");
    const [ trackList, setTrackList ] = useState([]);
    const [ dummy, setDummy ] = useState();
    const loginContext = useContext(LoginContext);
    const params = useParams();
    const navigate = useNavigate();

    function removeTrack(id) {
        let trackReq = new XMLHttpRequest();
        trackReq.open("DELETE", `http://127.0.0.1:8080/tracks/${id}`);

        trackReq.onload = () => {
            reloadData();
        };

        trackReq.send();
    }

    function handleTrackCreation(event) {
        const form = event.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        console.log(encodeURI(formJson.trackName))

        let trackReq = new XMLHttpRequest();
        trackReq.open("POST", `http://127.0.0.1:8080/tracks?trackName=${encodeURI(formJson.trackName)}&length=${formJson.minutes * 60 + parseInt(formJson.seconds)}&albumId=${params.albumid}&albumOrder=${trackList.length}`);

        trackReq.onload = () => {
            reloadData();
        };

        trackReq.send();
    }

    function reloadData() {
        let albumDataReq = new XMLHttpRequest();
        albumDataReq.open("GET", `http://127.0.0.1:8080/albums/${params.albumid}`);
        
        albumDataReq.onload = () => {
            let data = JSON.parse(albumDataReq.responseText);

            data.tracks.sort((a, b) => a.albumOrder - b.albumOrder);

            setAlbumName(data.name);
            setJacketURL(data.jacketURL);
            setTrackList(data.tracks);
        };
        
        albumDataReq.send();
    }

    function addToListened() {
        let albumReq = new XMLHttpRequest();
        albumReq.open("PUT", `http://127.0.0.1:8080/users/${loginContext.user.id}/albums?albumId=${params.albumid}`);

        albumReq.onload = () => {
            let userReq = new XMLHttpRequest();
            userReq.open("GET", `http://127.0.0.1:8080/users/${loginContext.user.id}`);

            userReq.onload = () => {
                let data = JSON.parse(userReq.responseText);
                loginContext.setUser(data);
            };

            userReq.send();
        };

        albumReq.send();
    }

    function maybeRenderListenedButton() {
        if (loginContext.user) {
            let hasListened = loginContext.user.listenedAlbums.some(e => e.id == params.albumid);

            return hasListened
                ? (<button className="albumControlsButton" disabled>In your listened albums</button>)
                : (<button onClick={ addToListened } className="albumControlsButton">Add to listened</button>);
        } else {
            return (<button className="albumControlsButton" disabled>Log in to mark as listened</button>);
        }
    }

    function deleteAlbum() {
        let albumReq = new XMLHttpRequest();
        albumReq.open("DELETE", `http://127.0.0.1:8080/albums/${params.albumid}`);

        albumReq.onload = () => {
            navigate("/albums");
        };

        albumReq.send();
    }

    const trackListElements = trackList.map(track => {
        let trackId = track.id;

        return (<tr key={ track.albumOrder }>
            <td className="textLeft trackName">{ track.name }</td>
            <td className="textRight">{ `${Math.floor(track.length / 60)}:${(track.length % 60).toString().padStart(2, "0")}` }</td>
            <td><button type="submit" className="createButton" onClick={ () => { removeTrack(trackId) } }>Remove</button></td>
        </tr>)
    });

    trackListElements.push(<tr><td colSpan="3">
        <button className="createButton createTrackButton"
            command="show-modal" commandfor="create-track-dialog">Add track...</button>
    </td></tr>);
    
    useEffect(() => { reloadData() }, []);

    return (
        <>
            <dialog id="create-track-dialog" closedby="any">
                <form method="dialog" onSubmit={ handleTrackCreation }>
                    <input required name="trackName" type="text" placeholder="Track name"></input>
                    <div className="timeContainer">
                        <input required name="minutes" type="number" min="0" placeholder="Minutes"></input>
                        <input required name="seconds" type="number" min="0" max="59" placeholder="Seconds"></input>
                    </div>
                    <button type="submit">Add</button>
                </form>
            </dialog>

            <div>
                <div className="albumWrapper">
                    <div className="albumLeftColumn">
                        <img className="jacketArt" src={ jacketURL }></img>
                        { maybeRenderListenedButton() }
                        <button onClick={ deleteAlbum } className="createButton albumControlsButton">Delete album...</button>
                    </div>
                    <div className="albumData">
                        <h1>{ albumName }</h1>
                        <table className="trackList">
                            <tbody>
                                { trackListElements }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Album;