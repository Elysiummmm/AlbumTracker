import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './album.css';

function Album({ albumId }) {
    const [ albumName, setAlbumName ] = useState("");
    const [ jacketURL, setJacketURL ] = useState("https://placehold.co/128");
    const [ trackList, setTrackList ] = useState([]);

    // this sucks you can't change order
    const trackListElements = trackList.map(track =>
        <tr>
            <td className="textLeft">{ track.name }</td>
            <td className="textRight">{ `${Math.floor(track.length / 60)}:${(track.length % 60).toString().padStart(2, "0")}` }</td>
        </tr>
    );

    let params = useParams();
    
    useEffect(() => {
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
    }, [])

    return (
        <>
            <div>
                <div className="albumWrapper"> 
                    <img className="jacketArt" src={ jacketURL }></img>
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