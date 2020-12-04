import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-rule-editor',
  templateUrl: './rule-editor.component.html',
  styleUrls: ['rule-editor.component.scss'],
})
export class RuleEditorComponent implements OnInit {
  message: string;

  constructor() {
    this.message = 'RuleEditorComponent message';
  }

  ngOnInit(): void {}
}
