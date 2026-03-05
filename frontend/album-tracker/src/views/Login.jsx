import { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { LoginContext } from "../LoginContext";

function Login() {
    const loginContext = useContext(LoginContext);
    const navigate = useNavigate();

    function handleLogin(event) {
        event.preventDefault();

        const form = event.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        let fetchUser = new XMLHttpRequest();
        fetchUser.open("GET", `http://127.0.0.1:8080/users/${formJson.userId}`);

        fetchUser.onload = () => {
            loginContext.setUser(JSON.parse(fetchUser.responseText));
            navigate(`/user/${formJson.userId}`);
        };

        fetchUser.send();
    }

    return (
        <>
            <form method="post" onSubmit={ handleLogin }>
                <input name="userId" type="number" placeholder="Your user ID"></input>
                <button type="submit">Log in</button>
            </form>

            <Link to="/register">
                Register
            </Link>
        </>
    )
}

export default Login;