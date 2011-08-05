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
package domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.mockito.*;
import org.junit.Before;
import org.junit.Test;

import repositories.ShipRepository;

import command.SetDepartureCommand;
import command.SetDepartureCommandHandler;
import events.DomainEventHandler;

/**
 * @author Nelson Lionel KEMINSE
 * 5 août 2011 14:22:00
 */
public class ShipTest {
	SetDepartureCommand command;
	SetDepartureCommandHandler commandHandler;
	DomainEventHandler eventHandler;
	Ship ship;
	
	@Before
	public void setUp() throws Exception {
		Port port = new Port("Douala", "Cameroon");
		Cargo cargo = new Cargo("Bindi");
		ship = new Ship("ID2011", "Baba", cargo, port);
		command = new SetDepartureCommand("ID2011", new Date(), "Havre", "France");
		eventHandler = new DomainEventHandler();
		assertEquals("The departure command update ship's port city", "Douala", ship.getPort().getCity());
	}

	@Test
	public final void testDepartureCommand() {
		
		IShipStore shipStore = Mockito.mock(IShipStore.class);
		
		ShipRepository shipRepository = new ShipRepository(eventHandler, shipStore);
		
		shipRepository.setShipEventHandler(eventHandler);
		
		commandHandler = new SetDepartureCommandHandler(command, shipRepository);
		
		//Scenario de test
		Mockito.when(shipStore.get("ID2011")).thenReturn(ship);

		//test
		commandHandler.execute();
		
		assertEquals(1, eventHandler.getLog().size());
		
		assertEquals("The departure command update ship's port city", "Havre", eventHandler.getLog().get(0).getShip().getPort().getCity());
		assertEquals("The departure command update ship's port country", "France", ship.getPort().getCountry());
		
		Mockito.verify(shipStore);
		
	}

}
