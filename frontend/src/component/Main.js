import React, {useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";

function Main() {
    const [nickname, setNickname] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8080/member', {
            headers: {
                Authorization: localStorage.getItem("Authorization")
            }
        })
            .then(response => {
                setNickname(response.data.nickname);
            })
            .catch(error => {
                console.error(error);
            });
    });

    return (
        <div>
            <h1>Welcome, {nickname}!</h1>
        </div>
    );
}

export default Main;
