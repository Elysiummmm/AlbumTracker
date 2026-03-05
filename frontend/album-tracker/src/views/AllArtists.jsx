import { useEffect, useRef, useState } from 'react';
import './AllArtists.css';
import { Link } from 'react-router-dom';

function AllArtists() {
    const [ artistList, setArtistList ] = useState([]);
    const artistId = useRef(0);

    function handleAlbumCreation(event) {
        const form = event.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        let albumReq = new XMLHttpRequest();
        albumReq.open("POST", `http://127.0.0.1:8080/albums?albumName=${formJson.albumName}&jacketURL=${formJson.jacketUrl}&artistId=${artistId.current}`);

        albumReq.onload = () => {
            reloadArtists();
        };

        albumReq.send();
    }

    {/* i'm literally john duplicated code */}
    function handleArtistCreation(event) {
        const form = event.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        let artistReq = new XMLHttpRequest();
        artistReq.open("POST", `http://127.0.0.1:8080/artists?artistName=${formJson.artistName}`);

        artistReq.onload = () => {
            reloadArtists();
        };

        artistReq.send();
    }

    function reloadArtists() {
        let artistDataReq = new XMLHttpRequest();
        artistDataReq.open("GET", `http://127.0.0.1:8080/artists`);
        
        artistDataReq.onload = () => {
            let data = JSON.parse(artistDataReq.responseText);
            setArtistList(data);
        };
        
        artistDataReq.send();
    }

    const artistListElements = artistList.map(artist => {
        const albums = artist.albums.map(album =>
            <Link key={ album.id } to={ `/album/${album.id}` }>
                <img src={ album.jacketURL } className="inlineJacket" title={ album.name }></img>
            </Link>
        );

        const setArtistId = () => { artistId.current = artist.id };

        albums.push(
            <button className="inlineJacket createButton" style={{ width: "215px" }}
                command="show-modal" commandfor="create-album-dialog"
                onClick={ setArtistId }>Add new...</button>
        );

        return (
            <tr key={ artist.id }>
                <td className="artistName">{ artist.name }</td>
                <td className="works">{ albums }</td>
            </tr>
        )
    });

    artistListElements.push(
        <tr><td colSpan="2">
            <button className="createButton" style={{ width: "100%" }}
            command="show-modal" commandfor="create-artist-dialog">Add new...</button>
        </td></tr>
    );

    useEffect(() => { reloadArtists() }, []);

    return (
        <>
            <dialog id="create-album-dialog" closedby="any">
                <form method="dialog" onSubmit={ handleAlbumCreation }>
                    <input required name="albumName" type="text" placeholder="Album name"></input>
                    <input required name="jacketUrl" type="url" placeholder="Jacket URL"></input>
                    <button type="submit">Add</button>
                </form>
            </dialog>

            <dialog id="create-artist-dialog" closedby="any">
                <form method="dialog" onSubmit={ handleArtistCreation }>
                    <input required name="artistName" type="text" placeholder="Artist name"></input>
                    <button type="submit">Add</button>
                </form>
            </dialog>
            
            <table className="artistList">
                <thead>
                    <tr className="artistListHead">
                        <td className="artistName">Artist</td>
                        <td>Works</td>
                    </tr>
                </thead>
                <tbody>
                    { artistListElements }
                </tbody>
            </table>
        </>
    )
}

export default AllArtists;