import React, {Component} from "react";
import '../../css/search.css';
import LobbyMenu from "../directive/lobby_menu";
import SearchTable from "../directive/search_table";
import LobbyModal from "../directive/lobby_modal";

class Search extends Component {

    constructor(props) {
        super(props);
        this.controller = props.controller;
        this.state = {
            modalMode: false
        };
        this.searchTable = React.createRef();
    }

    toggleModal() {
        this.setState({modalMode: !this.state.modalMode})
    }

    componentDidMount() {
        this.controller.subscribe('search', 'LOBBY_LIST_CHANGED', message => {
            this.searchTable.current.setLobbies(message.lobbies);
        });

        this.controller.subscribe('search', 'LOBBY_CREATED', message => {
            this.controller.goLobby(true, message.id);
        });

        this.controller.subscribe('search', 'JOINED_TO_LOBBY', message => {
            this.controller.goLobby(false, message.lobbyId);
        });

        this.controller.requestLobbies();
    }

    createLobby(numberGames) {
        this.controller.createLobby(numberGames)
    }

    joinLobby(id) {
        this.controller.joinLobby(id);
    }

    render() {
        return (
            <div className="search-background">
                <LobbyMenu lobbyButtonCallback={this.toggleModal.bind(this)} />
                <SearchTable ref={this.searchTable} onRowClick={this.joinLobby.bind(this)}/>
                {this.state.modalMode && <LobbyModal onOk={this.createLobby.bind(this)} onCancel={this.toggleModal.bind(this)}/>}
            </div>
        );
    }
}

export default Search;