/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import EdgeList from "./EdgeList";
import Grid from "./Grid";
import GridSizePicker from "./GridSizePicker";

// Allows us to write CSS styles inside App.css, any any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    gridSize: number;  // size of the grid to display
    textingSize:string;
    texting:string;
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            gridSize: 4,
            textingSize : "4",
            texting: "",
        };
    }

    updateGridSize = (newSize: number , text: string) => {
        this.setState({
            gridSize: newSize,
            textingSize :text,
        });
    };

    updateEdgeList = (text:string)=>{
        this.setState({
            texting :  text,
        })
    }

    render() {
        const canvas_size = 500;
        return (
            <div>
                <p id="app-title">Connect the Dots!</p>
                <GridSizePicker value={this.state.textingSize} onChange={this.updateGridSize} />
                <Grid size={this.state.gridSize} width={canvas_size} height={canvas_size}/>
                <EdgeList text = {this.state.texting} onChange={this.updateEdgeList}/>
            </div>

        );
    }

}

export default App;
