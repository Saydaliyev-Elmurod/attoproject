package org.example.service;

import org.example.container.ComponentContainer;
import org.example.dto.Terminal;
import org.example.enums.TerminalStatus;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repository.TerminalRepository;
@Setter
@Service
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;
    public void createTerminal(String number, String address) {
        Terminal terminal = terminalRepository.getTerminalByNumber(number);
        ////checking
        if (terminal!=null){
            System.out.println("This terminal already exists");
            return;
        }
        terminalRepository.createTerminal(number, address);
    }

    public void updateTerminalByNumber(String number, String address) {
        Terminal terminal = terminalRepository.getTerminalByNumber(number);
        if (terminal == null||!terminal.getAddress().equals(address)) {
            System.out.println("Terminal not found");
        } else {
            System.out.print("Enter new TERMINAL number : ");
            String newTerminalNum = ComponentContainer.stringScanner.next();
            System.out.print("Enter new TERMINAL address : ");
            String new_address = ComponentContainer.stringScanner.next();
            Terminal terminal1 = terminalRepository.getTerminalByNumber(newTerminalNum);
            if (terminal1 == null || terminal1.getNumber().equals(number)) {
                terminalRepository.updateTerminalByNumber(terminal, newTerminalNum, new_address);
                System.out.println("Successfully");
            } else {
                System.out.println("This terminal already exists");
            }
        }
    }

    public void changeTerminalStatus(String numCard, String address) {
        Terminal terminal = terminalRepository.getTerminalByNumber(numCard);
        if (terminal == null||!terminal.getAddress().equals(address)) {
            System.out.println("Card not found");
        } else {
            if (terminal.getStatus().equals(TerminalStatus.BLOCK)) {
                terminal.setStatus(TerminalStatus.ACTIVE);
            } else if (terminal.getStatus().equals(TerminalStatus.ACTIVE)) {
                terminal.setStatus(TerminalStatus.BLOCK);
            }
            terminalRepository.updateTerminalStatus(terminal);

        }
    }

    public void deleteTerminal(String numTerminal, String address) {
        Terminal terminal = terminalRepository.getTerminalByNumber(numTerminal);
        if (terminal == null || (!terminal.getAddress().equals(address))) {
            System.out.println("Terminal not found");
            return;
        }
        terminalRepository.deleteTerminal(terminal);
    }
}
