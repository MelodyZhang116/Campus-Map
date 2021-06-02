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
}
class App extends Component<{}, AppState> {
    constructor(props:any) {
        super(props);
        this.state = {
            text : "",
            parsedText : [],
        }
    }
    updateLocation = (parsedText:string[])=>{
        this.setState({
            parsedText:parsedText,
        })
    }
    updateTexting = (text:string)=>{
        this.setState({
            text:text,
        })
    }

    render() {
        return (
            <div>
                <MapView/>
                <LocationPicker text={this.state.text} onDraw={this.updateLocation} onChange={this.updateTexting}/>
            </div>
        );
    }

}

export default App;
