import { Component, OnInit } from '@angular/core';
import {Message, MessagesService} from "../messages.service";

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss'],
  providers: [MessagesService]
})
export class MessageListComponent implements OnInit {

  private messages: Message[] = [];

  constructor(private service: MessagesService) {}

  ngOnInit() {
    this.messages = this.service.getMessages();
    this.messages.sort((a, b) => {
        if(a.timestamp === b.timestamp) {
          return b.source > a.source?-1:1;
        } else {
          return a.timestamp > b.timestamp?-1:1;
        }
      }
    )
  }

}
