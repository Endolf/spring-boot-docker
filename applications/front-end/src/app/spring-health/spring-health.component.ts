import {Component, HostListener, Input, OnInit} from '@angular/core';
import {SpringHealth, SpringHealthDetail, SpringHealthService} from "../spring-health.service";

@Component({
  selector: 'app-spring-health',
  templateUrl: './spring-health.component.html',
  styleUrls: ['./spring-health.component.scss'],
  providers: [SpringHealthService]
})
export class SpringHealthComponent implements OnInit {

  errorMessage: string;
  status: string;
  health: SpringHealth[];

  @Input() healthUrl: string;
  @Input() name: string;

  constructor(private service: SpringHealthService) { }

  ngOnInit() {
    this.service.getSpringHealth(this.healthUrl).subscribe(healthItem => {
      console.log("Got health", healthItem);
      this.status = healthItem.status;
      this.health = healthItem.healthItems;
    }, error=>{
      console.log("Got error", error);
      this.status = "DOWN";
      this.errorMessage = error;
    },() =>{
      console.log("All done");
    });
  }
}
