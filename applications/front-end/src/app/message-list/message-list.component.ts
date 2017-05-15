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
  private errorMessage: string;

  constructor(private service: MessagesService) {}

  ngOnInit() {
    this.service.getMessages().subscribe(
      messages => this.messages = messages.sort((a, b) => {
          if(a.timestamp === b.timestamp) {
            return b.source > a.source?-1:1;
          } else {
            return a.timestamp > b.timestamp?-1:1;
          }
        }
      ),
      error =>  this.errorMessage = <any>error);
  }

}
