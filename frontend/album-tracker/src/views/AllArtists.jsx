import { useEffect, useState } from 'react';
import './AllArtists.css';
import { Link } from 'react-router-dom';

function AllArtists() {
    const [ artistList, setArtistList ] = useState([]);

    function handleAlbumCreation(event) {

    }

    const artistListElements = artistList.map(artist => {
        const albums = artist.albums.map(album =>
            <Link key={ album.id } to={ `/album/${album.id}` }>
                <img src={ album.jacketURL } className="inlineJacket" title={ album.name }></img>
            </Link>
        );

        albums.push(
            <button className="inlineJacket createButton" style={{ width: "215px" }}
                command="show-modal" commandfor="create-album-dialog">Add new...</button>
        );

        return (
            <tr key={ artist.id }>
                <td className="artistName">{ artist.name }</td>
                <td className="works">{ albums }</td>
            </tr>
        )
    });

    useEffect(() => {
        let artistDataReq = new XMLHttpRequest();
        artistDataReq.open("GET", `http://127.0.0.1:8080/artists`);
        
        artistDataReq.onload = () => {
            let data = JSON.parse(artistDataReq.responseText);
            setArtistList(data);
        };
        
        artistDataReq.send();
    }, []);

    return (
        <>
            <dialog id="create-album-dialog" closedby="any">
                <form method="dialog" onSubmit={ handleAlbumCreation }>
                    <input required name="albumName" type="text" placeholder="Album name"></input>
                    <input required name="jacketUrl" type="url" placeholder="Jacket URL"></input>
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