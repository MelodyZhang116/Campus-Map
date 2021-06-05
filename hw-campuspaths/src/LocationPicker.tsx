
import React, {Component} from 'react';

interface LocationPickerProps {
    buildings:Record<string,string>;
    onStart(start:string):void;
    onEnd(end:string):void;
}

class LocationPicker extends Component<LocationPickerProps> {
    /**
     * pass the data about starting location to the App through this method
     * @param event
     */
    onStartChange = (event:React.ChangeEvent<HTMLSelectElement>) =>{
        this.props.onStart(event.target.value);
    }

    /**
     * pass the data about ending location to the App through this method
     * @param event
     */
    onEndChange = (event:React.ChangeEvent<HTMLSelectElement>)=>{
        this.props.onEnd(event.target.value);
    }
    render() {
        let buildingsLongName = Object.values(this.props.buildings);
        return (
            <div>
                <p> This map shows the shortest path from a starting location to an ending location</p>
                <p> Select a path From</p>
                <select onChange={this.onStartChange}>
                    <option>Select a location to start</option>
                    {buildingsLongName.map(building =><option value={building}>{building}</option>)}
                </select>
                <p>To</p>
                <select onChange={this.onEndChange}>
                    <option>Select a location to end</option>
                    {buildingsLongName.map(building =><option value={building}>{building}</option>)}
                </select>

            </div>
        );
    }
}

export default LocationPicker;
