import React from "react";
import '../../css/lobby.css';
import MessageWindow from "../directive/message_window";

class Lobby extends React.Component {

    constructor(props) {
        super(props);
        this.controller = this.props.controller;
        let params = new URL(window.location.href).searchParams;
        this.state = {
            host: params.get('host'),
            id: params.get('id'),
            users: [],
            modalMode: false
        };
        this.modal = React.createRef();
    }

    render() {
        const users = this.state.users.map((user, index) => <div className="user" key={index}>
            <span>{index + 1}</span>
            <span className="user-name">{user.name}</span>
            {(this.state.host === 'true') && !user.host ? <span className="kik-button">KIK</span> : <span/>}
        </div>);
        return (
            <div className="background">
                <span className="title">USERS</span>
                <div className="user-container">
                    {users}
                </div>

                <div className="button-container">
                    {this.state.host === 'true' && <button className="lobby-button" >START</button>}
                    <button className="lobby-button" >LEAVE</button>
                </div>
                {this.state.modalMode && <MessageWindow ref={this.modal} />}
            </div>
        );
    }
}

export default Lobby;