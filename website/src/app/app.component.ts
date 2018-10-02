import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppService } from './app.service';
import { WebSocketSubject } from 'rxjs/webSocket';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  title = 'website';
  messages = [];
  message = '';
  JSON = JSON;
  private websocket: WebSocketSubject<Object>;

  constructor(
    private service: AppService
  ) {}

  ngOnInit(): void {
    this.service.list().subscribe((res) => {
      this.messages = res;
    });
    this.websocket = this.service.listen();
    this.websocket.subscribe((msg: Object) => {
      console.log(msg);
      this.messages.push(msg);
    }, (err) => {
      console.log(err);
    });
  }

  ngOnDestroy(): void {
    this.websocket.unsubscribe();
  }

  submit() {
    try {
      const msg = JSON.parse(this.message);
      this.service.create(msg).subscribe(() => {
        console.log('success');
      }, (err) => {
        console.log('error');
      });
    } catch (e) {
      alert(e);
    }

  }
}
