import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-validation-tester',
  templateUrl: './validation-tester.component.html',
  styleUrls: ['validation-tester.component.scss'],
})
export class ValidationTesterComponent implements OnInit {
  message: string;

  constructor() {
    this.message = 'ValidationTesterComponent message';
  }

  ngOnInit(): void {}
}
