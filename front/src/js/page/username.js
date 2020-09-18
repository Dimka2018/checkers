import React, {Component} from "react";
import '../../css/username.css';

class Username extends Component {

    constructor(props) {
        super(props);
        this.controller = props.controller;
    }

    componentDidMount() {
        this.controller.subscribe('username', 'USERNAME_CHANGED', message => {
            this.controller.goLobbySearch();
        });
        let username = window.localStorage.getItem("username");
        if (username) {
            this.controller.saveUsername(username);
        }
    }

    componentWillUnmount() {
        this.controller.unsubscribeAll();
    }

    handleInput(event) {
        this.setState({username: event.target.value})
    }

    saveUsername() {
        window.localStorage.setItem("username", this.state.username);
        this.controller.saveUsername(this.state.username);
    }

    render() {
        return (
            <div className='username-background'>
                <div className="username-form">
                    <div className='title'>Enter username</div>
                    <input onInput={this.handleInput.bind(this)} />
                    <button onClick={this.saveUsername.bind(this)}>Save</button>
                </div>
            </div>
        );
    }
}

export default Username;