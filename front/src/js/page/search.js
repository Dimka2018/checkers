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

    render() {
        return (
            <div className="search-background">
                <LobbyMenu lobbyButtonCallback={this.toggleModal.bind(this)} />
                <SearchTable ref={this.searchTable}/>
                {this.state.modalMode && <LobbyModal onCancel={this.toggleModal.bind(this)}/>}
            </div>
        );
    }
}

export default Search;