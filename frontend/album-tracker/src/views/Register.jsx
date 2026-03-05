import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { LoginContext } from "../LoginContext";

function Register() {
    const navigate = useNavigate();
    const loginContext = useContext(LoginContext);

    function createUser(event) {
        event.preventDefault();

        const form = event.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        let createUserReq = new XMLHttpRequest();
        createUserReq.open("POST", `http://127.0.0.1:8080/users?username=${formJson.name}&pfpURL=${formJson.pfpUrl}`);

        createUserReq.onload = () => {
            let fetchUser = new XMLHttpRequest();
            fetchUser.open("GET", `http://127.0.0.1:8080/users/${createUserReq.responseText}`);

            fetchUser.onload = () => {
                loginContext.setUser(JSON.parse(fetchUser.responseText));
                navigate(`/user/${createUserReq.responseText}`);
            };

            fetchUser.send();
        };

        createUserReq.send();
    }

    return (
        <>
            <form method="post" onSubmit={ createUser }>
                <input name="name" type="text" placeholder="Your username"></input>
                <input name="pfpUrl" type="url" placeholder="URL to your profile picture"></input>
                <button type="submit">Register</button>
            </form>
        </>
    )
}

export default Register;