import { useState, useEffect } from 'react';
import './AllAlbums.css';
import { Link } from 'react-router-dom';

function AllAlbums() {
    const [ albumList, setAlbumList ] = useState([]);

    const albumListElements = albumList.map(album => 
        <div className="albumListEntry" key={ album.id }>
            <Link to={ `/album/${album.id}` }>
                <img src={ album.jacketURL } className="albumListJacket"></img>
            </Link>
            <b style={{ fontSize: "22px" }}>{ album.name }</b>
        </div>
    );

    useEffect(() => {
        let albumDataReq = new XMLHttpRequest();
        albumDataReq.open("GET", `http://127.0.0.1:8080/albums`);
        
        albumDataReq.onload = () => {
            let data = JSON.parse(albumDataReq.responseText);
            setAlbumList(data);
        };
        
        albumDataReq.send();
    }, []);

    return (
        <>
            <div className="albumsList">
                { albumListElements }
            </div>
        </>
    )
}

export default AllAlbums;