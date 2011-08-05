/** 
 *   ShippingProcess Application.
 *
 *   Copyright 2011 Harmonic-Pharma
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://www.harmonic-pharma.com/ for more information 
 *   about this app.
 */
package command;

import repositories.ShipRepository;
import domain.Port;
import domain.Ship;
import events.DepartureHasBeenSettedEvent;

/**
 * @author Nelson Lionel KEMINSE
 * 5 août 2011 12:29:26
 */
public class SetDepartureCommandHandler extends AbsCommandHandler<SetDepartureCommand> {
	
	public SetDepartureCommandHandler(SetDepartureCommand command, ShipRepository shipRepository) {
		super(command, shipRepository);
	}

	public void execute() {
		Ship ship = shipRepository.getShipById(getCommand().getShipId());
		Port port = getCommand().getToPort();
		DepartureHasBeenSettedEvent event = new DepartureHasBeenSettedEvent(getCommand().getOccured(), ship, port);
		shipRepository.handleEvent(event);
	}
}
