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
import MapView from "./MapView";
import LocationPicker from "./LocationPicker";

interface AppState{
    text:string;
    parsedText:string[];
    path:[number,number][];
}
class App extends Component<{}, AppState> {
    constructor(props:any) {
        super(props);
        this.state = {
            text : "",
            parsedText : [],
            path: [],
        }
    }
    updateLocation = (parsedText:string[])=>{
        this.setState({
            parsedText:parsedText,
        })
        this.findPath();
    }
    updateTexting = (text:string)=>{
        this.setState({
            text:text,
        })
    }
    findPath= async () => {
        try {
            let response = await fetch("http://localhost:4567/path?start="+this.state.parsedText[0]
            +"&end="+this.state.parsedText[1]);
            console.log("http://localhost:4567/path?start="+this.state.parsedText[0]
                +"&end="+this.state.parsedText[1]);
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }

            let object = (await response.json())as [number,number][];
            this.setState({
                path:object,
            })
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    render() {
        return (
            <div>
                <MapView path={this.state.path}/>
                <LocationPicker text={this.state.text} onDraw={this.updateLocation} onChange={this.updateTexting}/>
            </div>
        );
    }

}

export default App;
